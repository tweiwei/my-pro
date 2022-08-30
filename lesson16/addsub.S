.text
.globl add_ins
add_ins:
    add a0, a0, a1          #a0 = a0+a1,a0、a1是C语言调用者传递的参数，a0是返回值，这样计算结果就返回了
    jr ra                   #函数返回

.globl sub_ins
sub_ins:
    sub a0, a0, a1          #a0 = a0-a1,a0、a1是C语言调用者传递的参数，a0是返回值，这样计算结果就返回了
    jr ra                   #函数返回
