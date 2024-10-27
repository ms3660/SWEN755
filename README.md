# **Thread Pool Demo: Sum of Prime Numbers**

### **Overview**
This project demonstrates the usage of **Java’s Executor Framework** by implementing a **thread pool** to calculate the sum of prime numbers within a given range. It splits the workload into smaller tasks, assigns them to a pool of threads, and aggregates the results. This project illustrates how to achieve parallel execution using **multi-threading** in Java.

---

## **Features**
- **Thread Pool Implementation**: Uses a fixed thread pool with 10 threads.
- **Callable Interface**: Each thread returns the sum of prime numbers for its assigned range.
- **Future Objects**: Collects results from multiple threads asynchronously.
- **Concurrency Management**: Efficiently manages tasks using thread pooling to reuse threads.

---

## **Requirements**
- **Java Development Kit (JDK)**: Version 8 or higher
- **IDE (optional)**: IntelliJ IDEA, Eclipse, or any text editor
- **Command Line (if running without IDE)**: Bash/Windows command prompt

---

## **Setup Instructions**

### **1. Compile the Java Files**
Ensure you are in the correct directory with the source files.
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
   This class implements the **`Callable<Long>` interface** to represent a task that calculates the sum of prime numbers for a specific range. Each task is executed by a thread from the thread pool.

2. **ThreadPoolDemo.java**  
   This class is the **main driver** of the program. It:
   - Creates a fixed thread pool with 10 threads.
   - Submits tasks to the thread pool.
   - Collects results using **`Future`** objects.
   - Prints the total sum of all prime numbers within the given range.

---

## **How the Program Works**
1. **Define the Range**: The range of numbers to be processed is **1 to 100,000**.
2. **Chunking the Range**: The range is divided into **10,000-number chunks**.
3. **Thread Pool Execution**:
   - A **fixed thread pool** with 10 threads is created.
   - Each thread processes a chunk of the range.
   - The sum of prime numbers for each chunk is calculated and returned.
4. **Result Aggregation**: The **main thread** collects the results from all threads and prints the total sum.

---

## **Example Output**
```
Processed range: 1 to 10000, Sum: 5736396
Processed range: 10001 to 20000, Sum: 6109171
Processed range: 20001 to 30000, Sum: 6792047
...
Total sum of prime numbers: 454396537
```

---

## **Technologies and Frameworks Used**
- **Java Executor Framework**: For thread pool management
- **Callable Interface**: To represent tasks with return values
- **Future Interface**: To manage the result of asynchronous computations

---
