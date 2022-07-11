/* *****************************************************************************
 *  Name:     Luis Gonzalez
 *  NetID:    1058634
 *
 *  Partner Name:     
 *  Partner NetID:     
 *
 *  Hours to complete assignment (optional): 25 hours
 *
 **************************************************************************** */

Programming Assignment 4: WordNet


/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in synsets.txt. Why did you make this choice?
 **************************************************************************** */
    To store synsets.txt I used sparate Chaining hash for a symbol table.
    I used two so searching words for keys or synset id as key would be easy
    I chose this so searching and insterting would be O(1) 

    * I did not write the code. I used SeparateChainingHashST.java from the 
    given code in BlackBoard
    https://www.blackboard.noctrl.edu/bbcswebdav/pid-1113208-dt-content-rid-10496317_1/courses/2021SP_CSCE_340_51840/SeparateChainingHashST.java

/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in hypernyms.txt. Why did you make this choice?
 **************************************************************************** */
    To store hypernyms.txt, I used a regular adjacency list. Where the outer
    container is an array of length V, and in each index of the array is a
    linked list to orgainize all the edges the V[i] is connected by.
    I decided to do this way, bc a lecture highly suggested to use
    adjacency list for directed hight dense graphs

/* *****************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithm? Express your answer as a function of the
 *  number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) 
 **************************************************************************** */

Description: Used DriectedCycle.java from BlackBoard. It takes in a digraph as
             an arguement and does Depth First Search for each Vertex. The dfs will
             check each Vertex if it as a cycle print in terminal if a cycle is found


Order of growth of running time: O(V+E)


/* *****************************************************************************
 *  Describe concisely your algorithm to compute the shortest common ancestor
 *  in ShortestCommonAncestor. For each method, give the order of growth of
 *  the best- and worst-case running times. Express your answers as functions
 *  of the number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) 
 *
 *  If you use hashing, assume the uniform hashing assumption so that put()
 *  and get() take constant time per operation.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't forget
 *  to count the time needed to initialize the marked[], edgeTo[], and
 *  distTo[] arrays.
 **************************************************************************** */

Description: To find the SCA, I only used two methods
             lenght() - for two known vertices and
             lengthSubset() - for two different subsets
             but the basic idea was done for both.
             First took in the two arguements 
             then called the BreadthFirstDirectedPaths constructor and passed in
             the graph and the source(s).
             BFS found the reachability of the source(s)
             after the Ctor call, lenght() and lengthSubset() ran a single
             for loop to check all the Vertex if it was a path to the source
             if the V does have a path, it checks the distTo the Source. 
             If min is found then it stores the V(ancestor)
             at the end of the loop it returns the minlenght.

                                 running time
method                  best case            worst case
--------------------------------------------------------
length()                E+V+get()                  E+V+get()

ancestor()              1                    1          - getancestor()

lengthSubset()          E+V+get()                  E+V+get()

ancestorSubset()        1                    1          - getancestor()



/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
    None that I know off
/
/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings and lectures, but do
 *  include any help from people (including
 *  classmates and friends) and attribute them by name.
 **************************************************************************** */
    None

/* *****************************************************************************
 *  Describe any serious problems you encountered.                    
 **************************************************************************** */
    Figuring what the Program wanted me to do. I was a lot to figure out
    Another problem I had was deciding what ST to use that would be fast and 
    interchangeable searching words and indexs as keys.
    Another serious problem I encounted is how to find sca.
    I also had trouble figuring out how to use SeparateChainingHashST and store
    each word. I had to do a lot of debugging to find out how it really works

/* *****************************************************************************
 *  If you worked with a partner, give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **************************************************************************** */
    I enjoyed the challenge. It was fun to see how to SeparateChainingHashST
    works and working with large data. I had fun making a menu to have an
    easier user experience.

    To Read Files
        I had all my files under a Data folder.
        so when the scanner will look inside the data folder >> " Data\\'filename.txt' "
        to remove it all you have to do is remove " Data\\" to read the file from the
        same folder as the program or just put all the data files under a Data folder 
        so the program can run.

/* *****************************************************************************
 *  Include the screenshots of your output.
 **************************************************************************** */
 All the output can be tested in the menu
