// function SimpleFunction.test 2
(SimpleFunction.test)
	@0
	D=A
	@LCL
	A=M+D
	M=0
	@1
	D=A
	@LCL
	A=M+D
	M=0
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
// not
	@SP
	A=M-1
	M=!M
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
// push argument 1 
	@1
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
// sub
	@SP
	D=M-1
	@SP
	M=D 
	@SP
	A=M
	D=M
	@SP
	A=M-1
	M=M-D
// return
	@LCL
	D=M
	@R13
	M=D
	@5
	A=D-A	
	D=M
	@R14
	M=D
	@SP
	D=M-1
	M=D
	A=D
	D=M
	@ARG
	A=M
	M=D
	@ARG
	D=M+1
	@SP
	M=D
	@R13
	D=M
	@1 
	A=D-A	
	D=M
	@THAT
	M=D
	@R13
	D=M
	@2 
	A=D-A	
	D=M
	@THIS
	M=D
	@R13
	D=M
	@3 
	A=D-A	
	D=M
	@ARG
	M=D	
	@R13
	D=M
	@4 
	A=D-A	
	D=M
	@LCL
	M=D	
	@R14
	A=M
	0;JMP
