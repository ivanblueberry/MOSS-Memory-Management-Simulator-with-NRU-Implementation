# MOSS Memory Management Simulator with NRU Implementation

[Read this in Spanish / Leer en Español](README_es.md)

## Summary
This repository contains the MOSS Memory Management Simulator, originally designed to demonstrate memory management techniques such as page replacement algorithms. The simulator has been enhanced with an implementation of the **NRU (Not Recently Used)** page replacement algorithm. NRU prioritizes page replacement by classifying pages based on their reference (R) and modification (M) bits.

## Features
- **Page Replacement Algorithm:** NRU implementation for more realistic memory management scenarios.
- **Trace Logging:** Detailed logging of memory operations for analysis in the default `tracefile`.
- **Configurable Simulation:** Customize the initial memory state and simulation parameters through `memory.conf`.
- **Test Scenarios:** Predefined commands to test and observe page replacement behavior.
- **Segmented Page View:** The GUI now displays pages divided into four segments of 4096 bytes each.
- **Low and High Memory Reading:** Support for commands with Low and High memory reads or writes (READ/WRITE) for hex/oct/bin numeric systems in the `commands` file.

## NRU Algorithm
The NRU algorithm classifies pages into four categories:
1. **Class 0:** R = 0, M = 0 (Not referenced, not modified - highest priority for replacement).
2. **Class 1:** R = 0, M = 1 (Not referenced, but modified).
3. **Class 2:** R = 1, M = 0 (Referenced, but not modified).
4. **Class 3:** R = 1, M = 1 (Referenced and modified - lowest priority for replacement).

When a page fault occurs, the algorithm replaces a page from the class with the lowest number. If multiple candidates exist within the same class, the first one found is replaced.

## Project Structure
- **`PageFault.java`:** Contains the NRU page replacement algorithm implementation.
- **`memory.conf`:** Configuration file to define the initial state of virtual and physical memory.
- **`commands`:** File with predefined memory operations (READ/WRITE) to test the simulator.
- **`tracefile`:** Log file showing the result of memory operations during the simulation.

## How to Compile
To compile all Java files:
```bash
javac -nowarn *.java
```

## How to Run
To run the simulator:
```bash
java MemoryManagement commands memory.conf
```

### Example Configuration (`memory.conf`)
- Pages 0 to 8 are marked as referenced and modified (R = 1, M = 1) to simulate occupied pages.
- Page 9 is neither referenced nor modified (R = 0, M = 0) and will be mapped during the simulation.
- Page 23 is not referenced but modified (R = 0, M = 1) and will be mapped during the simulation.
- Pages 24 and 31 are referenced but not modified (R = 1, M = 0) and will be mapped during the simulation.

```plaintext
// memset virtPage# physicalPage# R M inMemTime(ns) lastTouchTime(ns)
memset 0 0 1 1 0 0
memset 1 1 1 1 0 0
memset 2 2 1 1 0 0
...
memset 9 9 0 0 0 0
memset 23 23 0 1 0 0
memset 24 24 1 0 0 0
memset 31 31 1 0 0 0
```

### Example Configuration (`commands`)
- Pages 0 to 3 are used to test memory address reading by segments (to test functionality, make sure to remove the "//" symbols).
- Pages 33 to 37 are used to test page faults.

```plaintext
// Enter READ/WRITE commands into this file
// READ <OPTIONAL number type: bin/hex/oct> <virtual memory address or random>
// WRITE <OPTIONAL number type: bin/hex/oct> <virtual memory address or random>

// Segment-based memory address writing and reading
// WRITE hex 3FFF 2000 // Page 00: Segment 3, 4
// READ hex 7FFF 4000  // Page 01: Segment 1, 4
// WRITE hex BFFF F000 // Page 02: Segment 4; Page 3: Segment 4

// Write to an unloaded page to trigger page faults
READ hex 84FFF 84000 // Page 33: READ operation to trigger a page fault
READ hex 88FFF 88000 // Page 34: READ operation to trigger another page fault
READ hex 8CFFF 8C000 // Page 35: READ operation to trigger another page fault
READ hex 90FFF 90000 // Page 36: READ operation to trigger another page fault
READ hex 94FFF 94000 // Page 37: READ operation...
```

## Testing Instructions
1. Ensure `memory.conf` and `commands` are configured according to your needs.
2. Run the simulator as indicated in the "How to Run" section.
3. Observe the output in the terminal and the `tracefile` for details on page replacement and faults.

## Example Output in `tracefile`
After running the simulator, you can expect a `tracefile` with logs like this:
```plaintext
READ 84fff ... page fault
READ 84000 ... okay
READ 88fff ... page fault
READ 88000 ... okay
READ 8cfff ... page fault
READ 8c000 ... okay
READ 90fff ... page fault
READ 90000 ... okay
READ 94fff ... page fault
READ 94000 ... okay
```

## Credits
- Original MOSS Simulator: [Alex Reeder](https://www.ontko.com/moss/)
- NRU Implementation: Adapted for educational purposes.

---

Feel free to fork and extend this project to explore other page replacement algorithms or further improve the simulator!