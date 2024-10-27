// COMP 412 Reference Allocator (2024-1-2), k = 5
loadI  0 => r0                // from input block
loadI  1 => r1                // from input block
add    r0, r1  => r2          // vr20, vr12 => vr19
add    r0, r1  => r3          // vr20, vr12 => vr18
loadI  65536 => r4            // spill pr3 (vr18) => Mem[65536]
store  r3 => r4 
add    r0, r1  => r3          // vr20, vr12 => vr17
loadI  65540 => r4            // spill pr3 (vr17) => Mem[65540]
store  r3 => r4 
add    r0, r1  => r3          // vr20, vr12 => vr16
loadI  65544 => r4            // spill pr3 (vr16) => Mem[65544]
store  r3 => r4 
add    r0, r1  => r3          // vr20, vr12 => vr15
add    r0, r1  => r0          // vr20, vr12 => vr14
add    r1, r2  => r2          // vr12, vr19 => vr13
loadI  65548 => r4            // spill pr2 (vr13) => Mem[65548]
store  r2 => r4 
loadI  65536 => r2            // restore  Mem[65536] (vr18) => pr 2
load   r2 => r2 
add    r1, r2  => r2          // vr12, vr18 => vr11
loadI  65552 => r4            // spill pr2 (vr11) => Mem[65552]
store  r2 => r4 
loadI  65540 => r2            // restore  Mem[65540] (vr17) => pr 2
load   r2 => r2 
add    r1, r2  => r2          // vr12, vr17 => vr9
loadI  65556 => r4            // spill pr2 (vr9) => Mem[65556]
store  r2 => r4 
loadI  65544 => r2            // restore  Mem[65544] (vr16) => pr 2
load   r2 => r2 
add    r1, r2  => r2          // vr12, vr16 => vr7
add    r1, r3  => r3          // vr12, vr15 => vr5
add    r1, r0  => r0          // vr12, vr14 => vr3
loadI  65560 => r4            // spill pr0 (vr3) => Mem[65560]
store  r0 => r4 
loadI  65548 => r0            // restore  Mem[65548] (vr13) => pr 0
load   r0 => r0 
add    r1, r0  => r0          // vr12, vr13 => vr10
loadI  65552 => r1            // restore  Mem[65552] (vr11) => pr 1
load   r1 => r1 
add    r0, r1  => r1          // vr10, vr11 => vr8
loadI  65556 => r0            // restore  Mem[65556] (vr9) => pr 0
load   r0 => r0 
add    r1, r0  => r0          // vr8, vr9 => vr6
add    r0, r2  => r2          // vr6, vr7 => vr4
add    r2, r3  => r3          // vr4, vr5 => vr2
loadI  65560 => r2            // restore  Mem[65560] (vr3) => pr 2
load   r2 => r2 
add    r3, r2  => r2          // vr2, vr3 => vr1
loadI  1024 => r3             // from input block
store  r2 => r3               // vr1 => Mem[vr0]
output 1024                   // as in the input
