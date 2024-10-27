import java.util.Arrays;
import java.util.ArrayList;

public class Allocator {

    final int LOAD = 3;
    final int LOADI = 4;
    final int STORE = 5;
    final int ADD = 6;
    final int SUB = 7;
    final int MULT = 8;
    final int LSHIFT = 9;
    final int RSHIFT = 10;
    final int OUTPUT = 11;
    final int NOP = 12;

    private int[] pr;
    private int[] vr;
    private int[] nu;
    private int[] spill;
    private IRRow head;
    private int maxLive;
    private int spillLoc;
    private IRRow restoreRow;
    private ArrayList<Integer> regStack;
    private int marked;

    int ops = 0;

    public Allocator(IRRow head, int maxPR, int maxVR, int maxLive, int spill) {
        this.maxLive = maxLive > maxPR ? 1 : 0;
        this.pr = new int[maxPR - this.maxLive];
        this.vr = new int[maxVR];
        this.nu = new int[maxVR];
        this.spill = new int[maxVR - maxLive];
        for (int i = 0; i < vr.length; i++) {
            vr[i] = -1;
            nu[i] = -1;
        }
        for (int i = 0; i < this.spill.length; i++) {
            this.spill[i] = -1;
        }
        for (int i = 0; i < pr.length; i++) {
            pr[i] = -1;
        }
        this.head = head;
        this.spillLoc = spill;
        this.regStack = new ArrayList<>();
        for (int i = maxPR - this.maxLive - 1; i >= 0; i--) {
            regStack.add(i);
        }
        this.restoreRow = null;
        this.marked = -1;
    }

    public void allocate() {
        IRRow ir = head;
        while (ir != null) {
            ops++;
            if (ir.opcode >= LOAD && ir.opcode <= RSHIFT) {
                if (ir.opcode == STORE) {
                    ir.op1.PR = getPR(ir.op1.VR, ir.op1.NU, ir);
                    marked = ir.op1.PR;
                    ir.op3.PR = getPR(ir.op3.VR, ir.op3.NU, ir);
                    free(ir.op1);
                    free(ir.op3);
                } else if (ir.opcode == LOAD) {
                    ir.op1.PR = getPR(ir.op1.VR, ir.op1.NU, ir);
                    free(ir.op1);
                    ir.op3.PR = getPR(ir.op3.VR, ir.op3.NU, ir);
                    free(ir.op3);
                } else if (ir.opcode == LOADI) {
                    ir.op3.PR = getPR(ir.op3.VR, ir.op3.NU, ir);
                    free(ir.op3);
                } else if (ir.opcode >= ADD && ir.opcode <= RSHIFT) {
                    ir.op1.PR = getPR(ir.op1.VR, ir.op1.NU, ir);
                    marked = ir.op1.PR;
                    ir.op2.PR = getPR(ir.op2.VR, ir.op2.NU, ir);
                    free(ir.op1);
                    free(ir.op2);
                    ir.op3.PR = getPR(ir.op3.VR, ir.op3.NU, ir);
                    free(ir.op3);
                }
            }
            printRenamedRow(ir);
            ir = ir.next;
            marked = -1;
        }
        // head.printIR();
    }

    public int getPR(int vr, int nu, IRRow ir) {
        this.nu[vr] = nu;
        if (this.vr[vr] != -1) {
            return this.vr[vr];
        }
        int spillIdx = -1;
        for (int i = 0; i < spill.length; i++) {
            if (spill[i] == vr) {
                spillIdx = i;
                break;
            }
        }
        if (spillIdx != -1) {
            restoreRow = restore(vr, ir, spillIdx);
            if (restoreRow.next.op3.PR == -1) {
                int newPR = spill(vr, ir);
                return newPR;
            } else {
                printRenamedRows(restoreRow);
                int newPR = restoreRow.next.op3.PR;
                restoreRow = null;
                return newPR;
            }
        }
        if (regStack.size() > 0) {
            int pr = regStack.remove(regStack.size() - 1);
            this.pr[pr] = vr;
            this.vr[vr] = pr;
            return pr;
        }
        return spill(vr, ir);
    }

    public void free(Operant op) {
        if (op.NU == -1) {
            int pr = vr[op.VR];
            this.pr[pr] = -1;
            this.vr[op.VR] = -1;
            // nu[op.VR] = -1;
            regStack.add(pr);
        }
    }

    public int spill(int vr, IRRow ir) {
        int target = 0;
        int spillIdx = -1;
        for (int i = 0; i < nu.length; i++) {
            if (i != vr && this.vr[i] != marked) {
                target = nu[i] > nu[target] ? i : target;
            }
        }
        nu[target] = -1;
        pr[this.vr[target]] = vr;
        this.vr[vr] = this.vr[target];
        this.vr[target] = -1;
        for (int i = 0; i < spill.length; i++) {
            if (spill[i] == target || spill[i] == -1) { 
                spill[i] = target;
                spillIdx = i;
                break;
            }
        }
        IRRow spillRow = new IRRow(LOADI, spillLoc + 4 * spillIdx, pr.length);

        IRRow storeRow = new IRRow(STORE, this.vr[vr], pr.length);
        spillRow.next = storeRow;
        printRenamedRows(spillRow);
        if (restoreRow != null) {
            restoreRow.next.op3.PR = this.vr[vr];
            printRenamedRows(restoreRow);
            restoreRow = null;
        }
        return this.vr[vr];
    }

    public IRRow restore(int vr, IRRow ir, int idx) {   
        // System.out.println("restore");
        // System.out.println("spill: " + idx + " " + Arrays.toString(spill));
        // System.out.println("pr: " + Arrays.toString(pr));
        // System.out.println("vr: " + Arrays.toString(this.vr));

        IRRow first = new IRRow(LOADI, spillLoc + idx * 4, pr.length);
        int newPR = -1;
        if (regStack.size() > 0) {
            newPR = regStack.remove(regStack.size() - 1);
            pr[newPR] = vr;
            this.vr[vr] = newPR;
        }
        // for (int i = 0; i < pr.length; i++) {
        //     if (pr[i] == -1) {
        //         pr[i] = vr;
        //         this.vr[vr] = i;
        //         newPR = i;
        //         break;
        //     }
        // }
        IRRow second = new IRRow(LOAD, pr.length, newPR);
        first.next = second;
        second.prev = first;
        // spill[idx] = -1;
        return first;
    }

    public void printRenamedIR() {
        IRRow curr = head;
        while (curr != null) {
            if (curr.opcode == LOADI) {
                System.out.println(Renamer.tokenMap.get(curr.opcode) + " " + curr.op1.SR + " => r" + curr.op3.PR);
            } else if (curr.opcode == STORE || curr.opcode == LOAD) {
                System.out.println(Renamer.tokenMap.get(curr.opcode) + " r" + curr.op1.PR + " => r" + curr.op3.PR);
            } else if (curr.opcode == ADD || curr.opcode == SUB || curr.opcode == MULT || curr.opcode == LSHIFT || curr.opcode == RSHIFT) {
                System.out.println(Renamer.tokenMap.get(curr.opcode) + " r" + curr.op1.PR + ", r" + curr.op2.PR + " => r" + curr.op3.PR);
            } else if (curr.opcode == OUTPUT) {
                System.out.println("output " + curr.op1.SR);
            } else if (curr.opcode == NOP) {
                System.out.println("nop");
            }
            curr = curr.next;
        }
    }

    public void printRenamedRows(IRRow row) {
        IRRow curr = row;
        while (curr != null) {
            if (curr.opcode == LOADI) {
                System.out.println(Renamer.tokenMap.get(curr.opcode) + " " + curr.op1.SR + " => r" + curr.op3.PR);
            } else if (curr.opcode == STORE || curr.opcode == LOAD) {
                System.out.println(Renamer.tokenMap.get(curr.opcode) + " r" + curr.op1.PR + " => r" + curr.op3.PR);
            } else if (curr.opcode == ADD || curr.opcode == SUB || curr.opcode == MULT || curr.opcode == LSHIFT || curr.opcode == RSHIFT) {
                System.out.println(Renamer.tokenMap.get(curr.opcode) + " r" + curr.op1.PR + ", r" + curr.op2.PR + " => r" + curr.op3.PR);
            } else if (curr.opcode == OUTPUT) {
                System.out.println("output " + curr.op1.SR);
            } else if (curr.opcode == NOP) {
                System.out.println("nop");
            }
            curr = curr.next;
        }
    }

    public void printRenamedRow(IRRow row) {
        IRRow curr = row;
        if (curr.opcode == LOADI) {
            System.out.println(Renamer.tokenMap.get(curr.opcode) + " " + curr.op1.SR + " => r" + curr.op3.PR);
        } else if (curr.opcode == STORE || curr.opcode == LOAD) {
            System.out.println(Renamer.tokenMap.get(curr.opcode) + " r" + curr.op1.PR + " => r" + curr.op3.PR);
        } else if (curr.opcode == ADD || curr.opcode == SUB || curr.opcode == MULT || curr.opcode == LSHIFT || curr.opcode == RSHIFT) {
            System.out.println(Renamer.tokenMap.get(curr.opcode) + " r" + curr.op1.PR + ", r" + curr.op2.PR + " => r" + curr.op3.PR);
        } else if (curr.opcode == OUTPUT) {
            System.out.println("output " + curr.op1.SR);
        } else if (curr.opcode == NOP) {
            System.out.println("nop");
        }
    }
}
