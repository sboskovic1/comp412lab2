// COMP 412 Reference Allocator (2024-1-2), k = 5
loadI  128 => r0              // from input block
loadI  132 => r1              // from input block
loadI  136 => r2              // from input block
loadI  140 => r3              // from input block
loadI  65536 => r4            // spill pr0 (vr15) => Mem[65536]
store  r0 => r4 
loadI  144 => r0              // from input block
loadI  65540 => r4            // spill pr1 (vr16) => Mem[65540]
store  r1 => r4 
loadI  148 => r1              // from input block
loadI  65544 => r4            // spill pr2 (vr17) => Mem[65544]
store  r2 => r4 
loadI  152 => r2              // from input block
loadI  65548 => r4            // spill pr3 (vr18) => Mem[65548]
store  r3 => r4 
loadI  156 => r3              // from input block
loadI  65552 => r4            // spill pr0 (vr19) => Mem[65552]
store  r0 => r4 
loadI  160 => r0              // from input block
loadI  65556 => r4            // spill pr1 (vr20) => Mem[65556]
store  r1 => r4 
loadI  164 => r1              // from input block
loadI  65560 => r4            // spill pr2 (vr21) => Mem[65560]
store  r2 => r4 
loadI  168 => r2              // from input block
loadI  65564 => r4            // spill pr3 (vr23) => Mem[65564]
store  r3 => r4 
loadI  172 => r3              // from input block
loadI  65568 => r4            // spill pr0 (vr25) => Mem[65568]
store  r0 => r4 
loadI  176 => r0              // from input block
loadI  65572 => r4            // spill pr2 (vr29) => Mem[65572]
store  r2 => r4 
loadI  180 => r2              // from input block
loadI  65576 => r4            // spill pr3 (vr31) => Mem[65576]
store  r3 => r4 
loadI  184 => r3              // from input block
loadI  65580 => r4            // spill pr0 (vr33) => Mem[65580]
store  r0 => r4 
loadI  188 => r0              // from input block
loadI  65584 => r4            // spill pr1 (vr27) => Mem[65584]
store  r1 => r4 
loadI  192 => r1              // from input block
loadI  65588 => r4            // spill pr2 (vr35) => Mem[65588]
store  r2 => r4 
loadI  196 => r2              // from input block
loadI  65592 => r4            // spill pr3 (vr37) => Mem[65592]
store  r3 => r4 
loadI  200 => r3              // from input block
loadI  65596 => r4            // spill pr0 (vr39) => Mem[65596]
store  r0 => r4 
loadI  204 => r0              // from input block
load   r0 => r0               // Mem[vr46] => vr1
load   r3 => r3               // Mem[vr45] => vr44
load   r2 => r2               // Mem[vr43] => vr42
load   r1 => r1               // Mem[vr41] => vr40
loadI  65596 => r1            // restore  Mem[65596] (vr39) => pr 1
load   r1 => r1 
load   r1 => r1               // Mem[vr39] => vr38
loadI  65592 => r1            // restore  Mem[65592] (vr37) => pr 1
load   r1 => r1 
load   r1 => r1               // Mem[vr37] => vr36
loadI  65588 => r1            // restore  Mem[65588] (vr35) => pr 1
load   r1 => r1 
load   r1 => r1               // Mem[vr35] => vr34
loadI  65584 => r1            // restore  Mem[65584] (vr27) => pr 1
load   r1 => r1 
loadI  65580 => r2            // restore  Mem[65580] (vr33) => pr 2
load   r2 => r2 
store  r1 => r2               // vr27 => Mem[vr33]
load   r2 => r2               // Mem[vr33] => vr32
loadI  65576 => r2            // restore  Mem[65576] (vr31) => pr 2
load   r2 => r2 
load   r2 => r2               // Mem[vr31] => vr30
loadI  65572 => r2            // restore  Mem[65572] (vr29) => pr 2
load   r2 => r2 
load   r2 => r2               // Mem[vr29] => vr28
load   r1 => r1               // Mem[vr27] => vr26
loadI  65568 => r1            // restore  Mem[65568] (vr25) => pr 1
load   r1 => r1 
load   r1 => r1               // Mem[vr25] => vr24
loadI  65564 => r1            // restore  Mem[65564] (vr23) => pr 1
load   r1 => r1 
load   r1 => r1               // Mem[vr23] => vr22
loadI  65560 => r1            // restore  Mem[65560] (vr21) => pr 1
load   r1 => r1 
load   r1 => r1               // Mem[vr21] => vr4
loadI  65556 => r2            // restore  Mem[65556] (vr20) => pr 2
load   r2 => r2 
load   r2 => r2               // Mem[vr20] => vr6
loadI  65552 => r3            // restore  Mem[65552] (vr19) => pr 3
load   r3 => r3 
load   r3 => r3               // Mem[vr19] => vr8
loadI  65600 => r4            // spill pr0 (vr1) => Mem[65600]
store  r0 => r4 
loadI  65548 => r0            // restore  Mem[65548] (vr18) => pr 0
load   r0 => r0 
load   r0 => r0               // Mem[vr18] => vr10
loadI  65604 => r4            // spill pr1 (vr4) => Mem[65604]
store  r1 => r4 
loadI  65544 => r1            // restore  Mem[65544] (vr17) => pr 1
load   r1 => r1 
load   r1 => r1               // Mem[vr17] => vr12
loadI  65608 => r4            // spill pr2 (vr6) => Mem[65608]
store  r2 => r4 
loadI  65540 => r2            // restore  Mem[65540] (vr16) => pr 2
load   r2 => r2 
load   r2 => r2               // Mem[vr16] => vr14
loadI  65612 => r4            // spill pr3 (vr8) => Mem[65612]
store  r3 => r4 
loadI  65536 => r3            // restore  Mem[65536] (vr15) => pr 3
load   r3 => r3 
load   r3 => r3               // Mem[vr15] => vr13
add    r3, r2  => r2          // vr13, vr14 => vr11
add    r2, r1  => r1          // vr11, vr12 => vr9
add    r1, r0  => r0          // vr9, vr10 => vr7
loadI  65612 => r1            // restore  Mem[65612] (vr8) => pr 1
load   r1 => r1 
add    r0, r1  => r1          // vr7, vr8 => vr5
loadI  65608 => r0            // restore  Mem[65608] (vr6) => pr 0
load   r0 => r0 
add    r1, r0  => r0          // vr5, vr6 => vr3
loadI  65604 => r1            // restore  Mem[65604] (vr4) => pr 1
load   r1 => r1 
add    r0, r1  => r1          // vr3, vr4 => vr2
loadI  1024 => r1             // from input block
loadI  65600 => r0            // restore  Mem[65600] (vr1) => pr 0
load   r0 => r0 
store  r0 => r1               // vr1 => Mem[vr0]
output 1024                   // as in the input
output 176                    // as in the input
