
# **Thread Pool Demo: Sum of Prime Numbers**

### **Overview**
This project demonstrates the implementation of a **thread pool** in Java, using **Java’s Executor Framework** to calculate the sum of prime numbers within a large range. The workload is divided into smaller tasks and executed by a fixed pool of threads, showcasing efficient resource management and concurrency. Each thread is reused across multiple tasks, simulating a time-consuming, performance-intensive operation.

---

## **Features**
- **Thread Pool Implementation**: Uses a fixed thread pool of 10 threads.
- **Callable Interface**: Each thread returns the sum of prime numbers for its assigned range.
- **Future Objects**: Collects and aggregates results from multiple threads asynchronously.
- **Thread Reuse**: Demonstrates efficient thread reuse by having each thread process multiple tasks.

---

## **Requirements**
- **Java Development Kit (JDK)**: Version 8 or higher
- **IDE (optional)**: IntelliJ IDEA, Eclipse, or any text editor
- **Command Line (if running without an IDE)**: Bash/Windows Command Prompt

---

## **Setup Instructions**

### **1. Compile the Java Files**
Ensure you are in the directory containing `PrimeSumTask.java` and `ThreadPoolDemo.java`.

```bash
javac PrimeSumTask.java ThreadPoolDemo.java
```

### **2. Run the Program**
```bash
java ThreadPoolDemo
```

---

## **Program Structure**

1. **PrimeSumTask.java**  
   - Implements the **`Callable<Long>`** interface to calculate the sum of prime numbers within a specific range.
   - Each task processes a range of integers, identifies prime numbers, and calculates their sum.
   - Outputs the thread name to demonstrate reuse, showing which thread processes each range.

2. **ThreadPoolDemo.java**  
   - The main driver class of the program.
   - Initializes a fixed thread pool with 10 threads.
   - Divides a large number range into smaller chunks, each processed by `PrimeSumTask`.
   - Collects the results using `Future` objects and aggregates them.
   - Prints the total sum of prime numbers and the execution time.

---

## **How the Program Works**
1. **Define the Range**: The range of numbers to be processed is **1 to 1,000,000**.
2. **Chunking the Range**: The range is divided into **5,000-number chunks** to create multiple tasks, ensuring each thread in the pool is reused.
3. **Thread Pool Execution**:
   - A fixed thread pool with 10 threads is created.
   - Each thread processes a chunk of the range and computes the sum of primes within that chunk.
   - Each thread name is printed to show that the threads are reused.
4. **Result Aggregation**: The main thread collects results from each `Future` object and prints the total sum of prime numbers.

---

## **Example Output**
The output demonstrates thread reuse, showing each thread's involvement in processing different chunks:

```
Thread pool-1-thread-1 processed range: 1 to 5000, Sum: 1548136
Thread pool-1-thread-2 processed range: 5001 to 10000, Sum: 1692939
Thread pool-1-thread-3 processed range: 10001 to 15000, Sum: 1454168
Thread pool-1-thread-1 processed range: 15001 to 20000, Sum: 1609179
Thread pool-1-thread-2 processed range: 20001 to 25000, Sum: 1351463
...
Total sum of prime numbers: 454396537
Time taken with 10 threads: 3050 ms
```

---

## **Technologies and Frameworks Used**
- **Java Executor Framework**: Manages the thread pool and controls concurrency.
- **Callable Interface**: Represents the tasks with return values.
- **Future Interface**: Collects and aggregates the result of asynchronous computations.

