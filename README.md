# B+ Tree Project
Implementation of a B+ tree indexing system to manage academic papers.

## Overview
This project involves the implementation of a B+ tree indexing system to manage an increasing volume of published academic papers. The system includes both primary and secondary indexing mechanisms, designed to efficiently store and query data. A graphical user interface (GUI) is also provided to assist.

### Primary B+ Tree
The primary B+ tree is a clustered index based on the `paperId` field. It organizes and stores academic papers according to their unique identifiers.

### Secondary B+ Tree
The secondary B+ tree is an unclustered index based on the `journal` field, which is non-unique. The leaf nodes of this tree store paper IDs associated with each journal.

## Objectives
The project includes the implementation of the following functionalities on the B+ Trees:

1. **Add Paper**:
   - Adds a new paper to both the primary and secondary B+ trees.
   - The `paperId` field is used for the primary tree, while the `journal` field serves as the index for the secondary tree.

![image](https://github.com/user-attachments/assets/3bd20180-1ca4-4f9c-9594-dd17bfdde27a)


![image](https://github.com/user-attachments/assets/372bbdbe-27b7-440a-bf8f-796818b925ec)


2. **Search Paper**:
   - Searches for an academic paper in the primary B+ tree using a given `paperId`.

![image](https://github.com/user-attachments/assets/232c9fcd-3174-4aa7-bf85-91d55fac78a7)


3. **Search Journal**:
   - Searches for a journal in the secondary B+ tree using a given journal name.

![image](https://github.com/user-attachments/assets/5231f025-24d8-43e1-af9d-3611a10204b2)


4. **Print Primary Tree**:
   - Prints the structure of the primary B+ tree in depth-first order.

5. **Print Secondary Tree**:
   - Prints the structure of the secondary B+ tree in depth-first order.

## Project Structure

### GUI Classes
The following classes provide a GUI for debugging purposes:
- `CengGUI.java`
- `GUIInternalPrimaryNode.java`
- `GUIInternalSecondaryNode.java`
- `GUILevel.java`
- `GUIPrimaryLeafNode.java`
- `GUISecondaryLeafNode.java`
- `GUITreeNode.java`
- `WrapLayout.java`

### Main Files
These classes manage the parsing of input and the structure of the B+ trees:
- `CengScholar.java`
- `CengPaper.java`
- `ScholarNode.java`
- `ScholarNodePrimaryLeaf.java`
- `ScholarNodeSecondaryLeaf.java`
- `ScholarNodePrimaryIndex.java`
- `ScholarNodeSecondaryIndex.java`
- `ScholarNodeType.java`
- `ScholarParser.java`
- `ScholarTree.java`

### Implementation Files
The implementation is on the following files:
- `ScholarTree.java`
- `ScholarNodePrimaryIndex.java`
- `ScholarNodeSecondaryIndex.java`

## Input and Output

### Add Paper
- **Input**: `add|<paperId>|<journal>|<title>|<author>|`
- **Output**: None.

### Search
- **Search Paper**:
  - **Input**: `search1|<paperId>`
  - **Output**: Prints the visited nodes and data if found, or an error message if not.
- **Search Journal**:
  - **Input**: `search2|<journal>`
  - **Output**: Prints the visited nodes and associated paper records.

### Print
- **Primary Tree**:
  - **Input**: `print1`
  - **Output**: Prints the structure and data of the primary B+ tree.
- **Secondary Tree**:
  - **Input**: `print2`
  - **Output**: Prints the structure and data of the secondary B+ tree.

## Development:
- Programming Language: Java (Version 14).

