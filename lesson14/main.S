.text		
.global	main

main:					# main函数
	add t1,zero,1		# x6 = 1
	add t2,zero,2		# x7 = 2
	add t0,t1,t2		# x5 = x6 + x7
	add a0,zero,zero 	# x10 = 0 相当于main函数中的return 0
    ret
