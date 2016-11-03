import java.util.*;

public class simple0{
	
	protected ArrayStack<Integer> cpu;
	protected Integer[] memory;
	protected boolean zero, carry, negate, crash;
	protected static final int MEM_SIZE = 1000;
	protected static final int MAX_8BIT_NUM = 127;
	protected static final int MIN_8BIT_NUM = -128;
	protected static final int CPU_SIZE = 100;
	protected int cursor=0; //used in jumpz/n & instruController methods

	/**
	*simple0 contructs a simple0 computer system simulation
	*/
	public simple0(){
		cpu = new ArrayStack<Integer>();
		memory = new Integer[MEM_SIZE];
		zero=false;
		carry=false;
		negate=false;
		crash=false;
	}

	/**
	*insert - pushes a value into the cpu
	*@param val is the integer to be pushed into cpu
	*@precondition cpu.size() !> CPU_SIZE && -128<val<127
	*@precondition a new top item is pushed into cpu
	*/
	public void insert(int val){
		cpu.push(val);
		if(cpu.size()>CPU_SIZE){
			crash=true;
			System.out.println("System crashed, stack is overflowed");
		}
		if(val==0){
			zero=true; 
		}
		if(val<MIN_8BIT_NUM){
			carry=true;
			System.out.println(val + " is below 8 bit minimum");
		}
		if(val>MAX_8BIT_NUM){
			carry=true;
			System.out.println(val + " exceeds 8 bit maximum");
		}
		if(val<0){
			negate=true;
		}
	}

	/**
	*remove - remove an item off cpu(the number is lost)
	*@precondition cpu.size)()!= 0
	*@postcondition item on top of cpu is removed 
	*@throw System crashed, stack is underflowed
	*/
	public void remove(){
		try{
			cpu.pop();
			if(cpu.peek()==0){
				zero=true; 
			}
			if(cpu.peek()<MIN_8BIT_NUM){
				carry=true;
				System.out.println(cpu.peek() + " is below 8 bit minimum");
			}
			if(cpu.peek()>MAX_8BIT_NUM){
				carry=true;
				System.out.println(cpu.peek() + " exceeds 8 bit maximum");
			}
			if(cpu.peek()<0){
				negate=true;
			}
		}	
		catch(EmptyStackException e){
			crash = true;
			System.out.println("System crashed, stack is underflowed");
		}
	}

	/**
	*load - inserts the number from location addr in memory onto cpu
	*@param addr is the location of the value in the memory
	*@precondition memory[addr]!= null
	*@postcondition item in memory[addr] is pushed into cpu
	*@throw NullPointerException if precondition failed
	*/
	public void load(int addr){
		try{
			cpu.push(memory[addr]);
			if(cpu.size()>CPU_SIZE){
				crash=true;
				System.out.println("System crashed, stack is overflowed");
			}
			if(memory[addr]==0){
				zero=true; 
			}
			if(memory[addr]<MIN_8BIT_NUM){
				carry=true;
				System.out.println(memory[addr] + " is below 8 bit minimum");
			}
			if(memory[addr]>MAX_8BIT_NUM){
				carry=true;
				System.out.println(memory[addr] + " exceeds 8 bit maximum");
			}
			if(memory[addr]<0){
				negate=true;
			}
		}
		catch(NullPointerException e){
			System.out.println("NullPointerException: Failed to load, memory at slot " + addr + " is empty");
		}
	}

	/** 
	*store - removes a number off the cpu and places it into location addr in memory
	*@param addr is the location of the memory 
	*@precondition cpu.size()!=0
	*@postcondition an item from cpu is removed and placed in memory[addr]
	*@throw EmptyStackException if stack is empty
	*/
	public void store(int addr){
		try{
			memory[addr]=cpu.pop();
		}
		catch(EmptyStackException e){
			crash=true;
			System.out.println("System crashed, stack is underflowed");
		}
	}

	/**
	*add - removes top two items from cpu, adds them, inserts the sum back into cpu
	*@precondition cpu.size()>=2 & not empty
	*@postcondition a new top item is created by adding previous top two values in cpu
	*@throw EmptyStackException if stack only has one or less item
	*/
	public void add(){
		try{
			int sum = cpu.pop()+cpu.pop();
			cpu.push(sum);
			if(sum==0){
				zero=true; 
			}
			if(sum<MIN_8BIT_NUM){
				carry=true;
				System.out.println(sum + " is below 8 bit minimum");
			}
			if(sum>MAX_8BIT_NUM){
				carry=true;
				System.out.println(sum + " exceeds 8 bit maximum");
			}
			if(sum<0){
				negate=true;
			}
		}
		catch(EmptyStackException e){
			crash = true;
			System.out.println("System Crashed, stack is underflowed");
		}
	}

	/**
	*subtract - removes top two items from cpu, subtracts them, inserts the difference back into cpu
	*@precondition cpu.size()>=2 & not empty
	*@postcondition a new top item is created from subtracting previous top two values in cpu
	*@throw EmptyStackException if stack is empty
	*/
	public void subtract(){
		try{
			int difference = cpu.pop() - cpu.pop();
			cpu.push(difference);
			if(difference==0){
				zero=true; 
			}
			if(difference<MIN_8BIT_NUM){
				carry=true;
				System.out.println(difference + " is below 8 bit minimum");
			}	
			if(difference>MAX_8BIT_NUM){
				carry=true;
				System.out.println(difference + " exceeds 8 bit maximum");
			}
			if(difference<0){
				negate=true;
			}
		}
		catch(EmptyStackException e){
			crash = true;
			System.out.println("System Crashed, stack is underflowed");
		}
	}

	/**
	*multiply - removes top two items from cpu, multiply them, inserts the product back into cpu
	*@precondition cpu.size()>=2
	*@postcondition a new top item is created from multiplying previous top two values in cpu
	*@throw EmptyStackException if stack only has one or less item
	*/
	public void multiply(){
		try{
			int product = cpu.pop()*cpu.pop();
			cpu.push(product);
			if(product==0){
				zero=true; 
			}
			if(product<MIN_8BIT_NUM){
				carry=true;
				System.out.println(product + " is below 8 bit minimum");
			}
			if(product>MAX_8BIT_NUM){
				carry=true;
				System.out.println(product + " exceeds 8 bit maximum");
			}
			if(product<0){
				negate=true;
			}
		}
		catch(EmptyStackException e){
			crash = true;
			System.out.println("System Crashed, stack is underflowed");
		}
	}

	/**
	*divide - removes top two items from cpu, divide them, inserts the quotient back into cpu
	*@precondition cpu.size()>=2
	*@postcondition a new top item is created from dividing previous top two values in cpu
	*@throw 
	*	EmptyStackException if stack only has one or less item
	* 	ArithmeticException if diving by 0
	*/
	public void divide(){
		try{
			int quotient = cpu.pop()/cpu.pop();
			cpu.push(quotient);
			if(quotient==0){
				zero=true; 
			}
			if(quotient<MIN_8BIT_NUM){
				carry=true;
				System.out.println(quotient + " is below 8 bit minimum");
			}
			if(quotient>MAX_8BIT_NUM){
				carry=true;
				System.out.println(quotient + " exceeds 8 bit maximum");
			}
			if(quotient<0){
				negate=true;
			}
		}
		catch(ArithmeticException e){
			System.out.println("ArithmeticException: Cannot divide by 0.");
		}
		catch(EmptyStackException e){
			crash = true;
			System.out.println("System Crashed, stack is underflowed");
		}
	}

	/**
	*dump - displays the current contents of the cpu and processor flags
	*@return current contents of cpu and state of processor flags
	*/
	public String dump(){
		String flags = "\nProcessor Flags --> ";
		return (cpu.toString() + flags + "Zero:" + zero + "  Carry:" + carry + "  Negate:" + negate + "  Crash:" + crash);
	}

	/**
	*jumpz - jumps forward n instructions(or backward if n is negative) 
	*@param n is the number of instructions to skip
	*@precondition processor flag zero == true
	*@postcondition n number of instruction is skipped(forward or backward)
	*/
	public void jumpz(int n){
		if(zero==true){
			cursor = cursor+n;
		}
	}

	/**
	*jumpn - jumps forward n instructions(or backward if n is negative)
	*@param n is the number of instructions to skip
	*@precondition processor flag negate == true
	*@postcondition n number of instruction is skipped(forward or backward)
	*/
	public void jumpn(int n){
		if(negate==true){
			cursor = cursor+n;
		}
	}

	/**
	*instrController - reads through the instruction list and decide which to execute first
	*@param list is the ArrayList where instructions are stored
	*@preconditon list!=null
	*@postcondition list of instructions is successful read & executed
	*@return current contents in cpu if dump instruction is in the list
	*/
	public String instrController(ArrayList<String> list){
		String result = "";
		while(cursor<list.size()){
			if(list.get(cursor).startsWith("push")){
				String[] val = list.get(cursor).split(" ");
				int value = Integer.parseInt(val[1]);
				this.insert(value);
			}else if(list.get(cursor).startsWith("load")){
				String[] val = list.get(cursor).split(" ");
				int value = Integer.parseInt(val[1]);
				this.load(value);
			}else if(list.get(cursor).startsWith(("store"))){
				String[] val = list.get(cursor).split(" ");
				int value = Integer.parseInt(val[1]);
				this.store(value);
			}else if(list.get(cursor).startsWith("jumpz")){
				String[] val = list.get(cursor).split(" ");
				int value = Integer.parseInt(val[1]);
				this.jumpz(value);
			}else if(list.get(cursor).startsWith("jumpn")){
				String[] val = list.get(cursor).split(" ");
				int value = Integer.parseInt(val[1]);
				this.jumpn(value);
			}else if(list.get(cursor).equals("pop")){
				this.remove();
			}else if(list.get(cursor).equals("add")){
				this.add();
			}else if(list.get(cursor).equals("subtract")){
				this.subtract();
			}else if(list.get(cursor).equals("multiply")){
				this.multiply();
			}else if(list.get(cursor).equals("divide")){
				this.divide();
			}else if(list.get(cursor).equals("dump")){
				result=this.dump();
			}
			cursor++;
		}
		return result;
	}


}