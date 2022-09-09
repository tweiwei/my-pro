.text
.globl slli_ins
slli_ins:
    slli a0, a0, 4          #a0 = a0<<4,a0是C语言调用者传递的参数，a0也是返回值，这样计算结果就返回了
    jr ra                   #函数返回

.globl sll_ins
sll_ins:
    sll a0, a0, a1          #a0 = a0<<a1,a0、a1是C语言调用者传递的参数，a0是返回值，这样计算结果就返回了
    jr ra                   #函数返回

.globl srli_ins
srli_ins:
    srli a0, a0, 8          #a0 = a0>>8,a0是C语言调用者传递的参数，a0也是返回值，这样计算结果就返回了
    jr ra                   #函数返回

.globl srl_ins
srl_ins:
    srl a0, a0, a1          #a0 = a0>>a1,a0、a1是C语言调用者传递的参数，a0是返回值，这样计算结果就返回了
    jr ra                   #函数返回

.globl srai_ins
srai_ins:
    srai a0, a0, 8          #a0 = a0>>8,a0是C语言调用者传递的参数，a0也是返回值，这样计算结果就返回了
    jr ra                   #函数返回

.globl sra_ins
sra_ins:
    sra a0, a0, a1          #a0 = a0>>a1,a0、a1是C语言调用者传递的参数，a0是返回值，这样计算结果就返回了
    jr ra                   #函数返回