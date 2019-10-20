// push constant 7 
	@7
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D// push constant 8 
	@8
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D// add
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
