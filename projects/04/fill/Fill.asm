// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.
(BEGIN)
	@SCREEN
	D=A
	@i 
	M=D // i = 16384 (screens memory map base address)

	@8192 // number of 16 bit words from screen memory map
	D=A
	@SCREEN
	D=D+A
	@n
	M=D // n = 16384 + 8192 

(LOOP)	
	@i
	D=M
	@n
	D=D-M
	@BEGIN
	D;JGE // if(i > n) goto END
	
	@KBD // keyboard memory map address
	D=M
	@SETCOLOR
	D;JNE // if(MEM[KBD] != 0) set color else no color (white)

	@color
	M=0
	@PAINT
	0;JMP

(SETCOLOR)		
	@color
	M=-1

(PAINT)
	@color
	D=M
	@i
	A=M
	M=D // paint 

	@i
	M=M+1 // i++

	@LOOP
	0;JMP

(END)
	@END
	0;JMP

