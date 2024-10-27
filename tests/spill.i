// Run with 4 registers
loadI 2 => r0
loadI 4 => r1
loadI 8 => r7
loadI 16 => r8
load r0 => r2
add r0, r1 => r3
add r0, r3 => r4
add r3, r4 => r5
mult r0, r1 => r6
mult r6, r5 => r3
sub r7, r8 => r2