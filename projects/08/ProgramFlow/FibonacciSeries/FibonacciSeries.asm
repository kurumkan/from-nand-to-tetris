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
// push constant 0 
	@0
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// pop that 0 
	@SP
	D=M-1
	@SP
	M=D
	@0
	D=A
	@THAT
	D=M+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// push constant 1 
	@1
	D=A
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// pop that 1 
	@SP
	D=M-1
	@SP
	M=D
	@1
	D=A
	@THAT
	D=M+D
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
// push constant 2 
	@2
	D=A
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
// pop argument 0 
	@SP
	D=M-1
	@SP
	M=D
	@0
	D=A
	@ARG
	D=M+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// label MAIN_LOOP_START
(MAIN_LOOP_START)
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
// if-goto COMPUTE_ELEMENT   @SP
	D=M-1
	@SP
	M=D
	@SP 
	A=M
	D=M
	@COMPUTE_ELEMENT
	D;JNE// goto END_PROGRAM
	@END_PROGRAM
	0;JMP
// label COMPUTE_ELEMENT
(COMPUTE_ELEMENT)
// push that 0 
	@0
	D=A
	@THAT
	A=M+D
	D=M
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// push that 1 
	@1
	D=A
	@THAT
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
// pop that 2 
	@SP
	D=M-1
	@SP
	M=D
	@2
	D=A
	@THAT
	D=M+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// push pointer 1 
	@1
	D=A
	@R3
	A=A+D
	D=M
	@SP
	A=M
	M=D
	@SP
	D=M+1
	@SP
	M=D
// push constant 1 
	@1
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
// push constant 1 
	@1
	D=A
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
// pop argument 0 
	@SP
	D=M-1
	@SP
	M=D
	@0
	D=A
	@ARG
	D=M+D
	@R13
	M=D
	@SP
	A=M
	D=M
	@R13
	A=M
	M=D
// goto MAIN_LOOP_START
	@MAIN_LOOP_START
	0;JMP
// label END_PROGRAM
(END_PROGRAM)
