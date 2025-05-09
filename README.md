# NFA Simulator and Visualizer

This project is a Java implementation of a Non-Deterministic Finite Automaton (NFA) simulator with graphical visualization. It allows users to define an NFA, input strings, and check whether the NFA accepts the strings. The NFA is displayed graphically, showing states, transitions, and acceptance conditions.

## Features

- **NFA Definition**: Users can input the alphabet, states, initial state, final states, and transitions.
- **String Validation**: Checks if an input string is accepted by the NFA.
- **Graphical Visualization**: Displays the NFA as a graph with states (circles), transitions (arrows), and labels for input symbols.
- **Support for ε/λ Transitions**: Handles empty string transitions (epsilon/lambda).
- **Interactive Input**: Guides users through inputting NFA components and validates inputs.

## How to Use

1. **Input the Alphabet**: Enter the symbols of the alphabet separated by spaces (e.g., `a b c`).
2. **Input the States**: Enter the names of the states separated by spaces (e.g., `q0 q1 q2`).
3. **Specify the Initial State**: Enter the name of the initial state (must be one of the defined states).
4. **Specify the Final States**: Enter the names of the final states separated by spaces (must be a subset of the defined states).
5. **Define Transitions**: Enter the number of transitions and then define each transition in one of the following formats:
   - `δ(q0, a) = q1`
   - `f(q0, a) = q1`
   - `q0, a = q1`
   - `q0 a q1`
   - `q0, a, q1`
6. **Input a String**: Enter a string to check if it is accepted by the NFA. The string must consist of symbols from the defined alphabet.
7. **View Results**: The program will display whether the string is accepted or rejected and show the NFA graphically.

## Example

### Input:
- Alphabet: `a b`
- States: `q0 q1 q2`
- Initial State: `q0`
- Final States: `q2`
- Transitions:
  - `δ(q0, a) = q1`
  - `δ(q1, b) = q2`
- String: `a b`

### Output:
- The NFA accepts the string `a b`.
- A graphical representation of the NFA is displayed.

## Report:
- The report file provides a thorough explanation with examples in Persian.
