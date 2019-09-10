// Multiplies R0 and R1 and stores the result in R2.
		@2 //sum
		M=0 // sum = 0
		@0 // R0
		D=M // D=R0
		@4 // i
		M=D // i = R0
(LOOP)	
		// decrementing 
		@4 // i
		M=M-1 // i--	

		// break condition
		D=M	
		@END
		D;JLT // if(i < 0) break;

		// calculating sum	
		@1 // R1
		D=M // D = R1
		@2  // sum
		M=M + D // sum=sum+R1

		
		@LOOP
		0;JMP // goto beginning of loop
(END)
		@END
		0;JMP		
