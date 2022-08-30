.text
.globl andi_ins
andi_ins:
    andi a0, a0, 0xff       #a0 = a0&0xff,a0是C语言调用者传递的参数，a0也是返回值，这样计算结果就返回了
    jr ra                   #函数返回

.globl and_ins
and_ins:
    and a0, a0, a1          #a0 = a0&a1,a0、a1是C语言调用者传递的参数，a0是返回值，这样计算结果就返回了
    jr ra                   #函数返回

.globl ori_ins
ori_ins:
    ori a0, a0, 0           #a0 = a0|0,a0是C语言调用者传递的参数，a0也是返回值，这样计算结果就返回了
    jr ra                   #函数返回

.globl or_ins
or_ins:
    or a0, a0, a1           #a0 = a0|a1,a0、a1是C语言调用者传递的参数，a0是返回值，这样计算结果就返回了
    jr ra                   #函数返回

.globl xori_ins
xori_ins:
    xori a0, a0, 0          #a0 = a0^0,a0是C语言调用者传递的参数，a0也是返回值，这样计算结果就返回了
    jr ra                   #函数返回

.globl xor_ins
xor_ins:
    xor a0, a0, a1          #a0 = a0^a1,a0、a1是C语言调用者传递的参数，a0是返回值，这样计算结果就返回了
    jr ra                   #函数返回
