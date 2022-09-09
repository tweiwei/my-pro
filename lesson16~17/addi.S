.text
.globl addi_ins
addi_ins:
    addi a0, a0, 5          #a0 = a0+5,a0是参数，又是返回值，这样计算结果就返回了
    jr ra                   #函数返回

.globl addi_ins2
addi_ins2:
    addi a0, a0, -2048       #a0 = a0-2048,a0是参数，又是返回值，这样计算结果就返回了
    jr ra                   #函数返回