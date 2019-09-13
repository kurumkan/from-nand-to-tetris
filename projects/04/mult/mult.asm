// Multiplies R0 and R1 and stores the result in R2.
	@R2 //sum
	M=0 // sum = 0
	@R0 // R0
	D=M // D=R0
	@R4 // i
	M=D // i = R0
(LOOP)	
	// decrementing 
	@R4 // i
	M=M-1 // i--	

	// break condition
	D=M	
	@END
	D;JLT // if(i < 0) break;

	// calculating sum	
	@R1 // R1
	D=M // D = R1
	@R2  // sum
	M=M + D // sum=sum+R1

	
	@LOOP
	0;JMP // goto beginning of loop
(END)
	@END
	0;JMP		
