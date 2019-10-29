// function Sys.init 0
(Sys.init)
// push constant 4000 
	@4000
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// pop pointer 0 
	@SP
	D=M-1
	@SP
	M=D
	@0
	D=A
	@R3
	D=A+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// push constant 5000 
	@5000
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// pop pointer 1 
	@SP
	D=M-1
	@SP
	M=D
	@1
	D=A
	@R3
	D=A+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// call Sys.main 0
	@RETURN_ADDRESS_0
	D=A
	@SP
	A=M
	M=D
	@SP
	M=M+1
	@LCL
	D=M
	@SP
	A=M
	M=D
	@SP
	M=M+1
	@ARG
	D=M
	@SP
	A=M
	M=D
	@SP
	M=M+1
	@THIS
	D=M
	@SP
	A=M
	M=D
	@SP
	M=M+1
	@THAT
	D=M
	@SP
	A=M
	M=D
	@SP
	M=M+1
	@5
	D=A
	@0
	D=D+A
	@SP
	D=M-D
	@ARG
	M=D
	@SP
	D=M
	@LCL
	M=D
	@Sys.main
	0;JMP
(RETURN_ADDRESS_0)
// pop temp 1 
	@SP
	D=M-1
	@SP
	M=D
	@1
	D=A
	@R5
	D=A+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// label LOOP
(LOOP)
// goto LOOP
	@LOOP
	0;JMP
// function Sys.main 5
(Sys.main)
@0
	D=A
	@SP
	A=M
	M=D
	@SP
	M=M+1
@0
	D=A
	@SP
	A=M
	M=D
	@SP
	M=M+1
@0
	D=A
	@SP
	A=M
	M=D
	@SP
	M=M+1
@0
	D=A
	@SP
	A=M
	M=D
	@SP
	M=M+1
@0
	D=A
	@SP
	A=M
	M=D
	@SP
	M=M+1
// push constant 4001 
	@4001
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// pop pointer 0 
	@SP
	D=M-1
	@SP
	M=D
	@0
	D=A
	@R3
	D=A+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// push constant 5001 
	@5001
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// pop pointer 1 
	@SP
	D=M-1
	@SP
	M=D
	@1
	D=A
	@R3
	D=A+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// push constant 200 
	@200
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// pop local 1 
	@SP
	D=M-1
	@SP
	M=D
	@1
	D=A
	@LCL
	D=M+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// push constant 40 
	@40
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// pop local 2 
	@SP
	D=M-1
	@SP
	M=D
	@2
	D=A
	@LCL
	D=M+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// push constant 6 
	@6
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// pop local 3 
	@SP
	D=M-1
	@SP
	M=D
	@3
	D=A
	@LCL
	D=M+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// push constant 123 
	@123
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// call Sys.add12 1
	@RETURN_ADDRESS_1
	D=A
	@SP
	A=M
	M=D
	@SP
	M=M+1
	@LCL
	D=M
	@SP
	A=M
	M=D
	@SP
	M=M+1
	@ARG
	D=M
	@SP
	A=M
	M=D
	@SP
	M=M+1
	@THIS
	D=M
	@SP
	A=M
	M=D
	@SP
	M=M+1
	@THAT
	D=M
	@SP
	A=M
	M=D
	@SP
	M=M+1
	@5
	D=A
	@1
	D=D+A
	@SP
	D=M-D
	@ARG
	M=D
	@SP
	D=M
	@LCL
	M=D
	@Sys.add12
	0;JMP
(RETURN_ADDRESS_1)
// pop temp 0 
	@SP
	D=M-1
	@SP
	M=D
	@0
	D=A
	@R5
	D=A+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// push local 0 
	@0
	D=A
	@LCL
	A=M+D
	D=M
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// push local 1 
	@1
	D=A
	@LCL
	A=M+D
	D=M
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// push local 2 
	@2
	D=A
	@LCL
	A=M+D
	D=M
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// push local 3 
	@3
	D=A
	@LCL
	A=M+D
	D=M
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// push local 4 
	@4
	D=A
	@LCL
	A=M+D
	D=M
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// add
	@SP
	D=M-1
	@SP
	M=D
	@SP
	A=M
	D=M
	@SP
	A=M-1
	M=D+M
// add
	@SP
	D=M-1
	@SP
	M=D
	@SP
	A=M
	D=M
	@SP
	A=M-1
	M=D+M
// add
	@SP
	D=M-1
	@SP
	M=D
	@SP
	A=M
	D=M
	@SP
	A=M-1
	M=D+M
// add
	@SP
	D=M-1
	@SP
	M=D
	@SP
	A=M
	D=M
	@SP
	A=M-1
	M=D+M
// return
	@LCL // FRAME=LCL
	D=M
	@R14
	M=D

	@R14
	D=M
	@5 // RET = *(FRAME-5)
	A=D-A	
	D=M
	@R15
	M=D

	@SP //*ARG = pop() 
	D=M-1 
	@SP
	M=D
	@SP
	A=M
	D=M
	@ARG
	A=M
	M=D


	@ARG // SP = ARG+1 
	D=M+1
	@SP
	M=D

	@R14 // THAT = *(FRAME-1)
	D=M

	@1 
	A=D-A	
	D=M
	@THAT
	M=D	

	@R14 // THIS = *(FRAME-2)
	D=M

	@2 
	A=D-A	
	D=M
	@THIS
	M=D	

	@R14 // ARG = *(FRAME-3)
	D=M

	@3 
	A=D-A	
	D=M
	@ARG
	M=D	

	@R14 // LCL = *(FRAME-4)
	D=M

	@4 
	A=D-A	
	D=M
	@LCL
	M=D	

	@R15 //goto RET
	A=M
	0;JMP
// function Sys.add12 0
(Sys.add12)
// push constant 4002 
	@4002
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// pop pointer 0 
	@SP
	D=M-1
	@SP
	M=D
	@0
	D=A
	@R3
	D=A+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// push constant 5002 
	@5002
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// pop pointer 1 
	@SP
	D=M-1
	@SP
	M=D
	@1
	D=A
	@R3
	D=A+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// push argument 0 
	@0
	D=A
	@ARG
	A=M+D
	D=M
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// push constant 12 
	@12
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// add
	@SP
	D=M-1
	@SP
	M=D
	@SP
	A=M
	D=M
	@SP
	A=M-1
	M=D+M
// return
	@LCL // FRAME=LCL
	D=M
	@R14
	M=D

	@R14
	D=M
	@5 // RET = *(FRAME-5)
	A=D-A	
	D=M
	@R15
	M=D

	@SP //*ARG = pop() 
	D=M-1 
	@SP
	M=D
	@SP
	A=M
	D=M
	@ARG
	A=M
	M=D


	@ARG // SP = ARG+1 
	D=M+1
	@SP
	M=D

	@R14 // THAT = *(FRAME-1)
	D=M

	@1 
	A=D-A	
	D=M
	@THAT
	M=D	

	@R14 // THIS = *(FRAME-2)
	D=M

	@2 
	A=D-A	
	D=M
	@THIS
	M=D	

	@R14 // ARG = *(FRAME-3)
	D=M

	@3 
	A=D-A	
	D=M
	@ARG
	M=D	

	@R14 // LCL = *(FRAME-4)
	D=M

	@4 
	A=D-A	
	D=M
	@LCL
	M=D	

	@R15 //goto RET
	A=M
	0;JMP
