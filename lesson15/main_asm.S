.text                   //表明下列代码放在text段中
.globl	main            //导出main符号，链接器必须要找的到main符号
main:
	addi sp,sp,-16
	sw	 s0,12(sp)      //保存s0寄存器
	addi s0,sp,16
	call C_function     //调用C语言编写的C_function函数
    li	 a0,0           //设置main函数的返回值为0
	lw	 s0,12(sp)      //恢复s0寄存器
	addi sp,sp,16
	jr	 ra             //返回