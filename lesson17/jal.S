.text
.global jal_ins
jal_ins:
    jal a0, imm_l1              #a0=add x0,x0,x0的地址，跳转到imm_l1地址处开始运行
    add x0, x0, x0              #相当于nop，什么也不做
    add x0, x0, x0
imm_l1:
    jal a0, imm_l2              #a0=imm_l2的地址，跳转到imm_l2地址处开始运行
imm_l2:
    jr ra                       #函数返回

.global jalr_ins
jalr_ins:
    addi sp, sp, -16            #在栈中分配16字节的空间
    sw  ra, 0(sp)               #保存ra寄存器到栈中
    jalr ra, a0, 0              #ra = lw ra, 0(sp)指令的地址，跳转到a0+0的地址处开始运行
    lw ra, 0(sp)                #从栈中恢复ra寄存器
    addi sp, sp, 16             #回收栈中分配的16字节的空间
    jr ra                       #函数返回
