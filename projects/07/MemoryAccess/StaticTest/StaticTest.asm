// push constant 111 
	@111
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// push constant 333 
	@333
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// push constant 888 
	@888
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// pop static 8 
	@SP
	D=M-1
	@SP
	M=D
	@8
	D=A
	@16
	D=A+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// pop static 3 
	@SP
	D=M-1
	@SP
	M=D
	@3
	D=A
	@16
	D=A+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// pop static 1 
	@SP
	D=M-1
	@SP
	M=D
	@1
	D=A
	@16
	D=A+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// push static 3 
	@3
	D=A
	@16
	A=A+D
	D=M
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// push static 1 
	@1
	D=A
	@16
	A=A+D
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
// push static 8 
	@8
	D=A
	@16
	A=A+D
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
