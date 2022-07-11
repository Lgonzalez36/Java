/******************************************************************
* ------------------------- TO DO -------------------------------- 
*                  Jeff's steps 1,5,6 completed[1,0,0] 
*                  Luis's steps 2,3,4 completed[2,3,4,5,6]
* ---------------------------------------------------------------- 
* 1. Create Random input files 
*      - create a single txt file of n "files" n = (100, 200) 
*      - make n modifiable by the user input 
*      - each fake n files is 0-1,000,000 
* 2. Read file 
*      - read file line by line and insert it sssss
* 3. Insert to priority Queue
*      - each line read is a "file size" 
*      - insert each line to priority Queue 
* 4. Insert files into disk of size 1GB from decending order 
*      - Call max - if < 1GB, add to disk 
*      - store max1 - Call max2 - add max1 and max2 
* 5. Sort Disk by Worst-fit, Decending Order 
*      - sort the Disk from most space avaiable to least
* 6. print Disk in Decending Order 
*      - If n is < 100 print out the disk in decreasing order 
*      - Id, Space Left, list of Files[]
*****************************************************************
*/


/**********************************************************************
*      Problem with my partner (second time)
 **********************************************************************/
Prof, My partner was Jeff G, He was not a very good partner. The first 
assignment we worked together he did not do anything. I gave him a 
second chance and he did very little. His only contribution was the 
creation of file sizes. The rest was my work. I don't think he deserves 
the grade we had last HW and also does not deserve a good grade from 
my hard work for this assignment. I want to work alone for the rest 
of the semester. He just slows me down and gives me more stress.
 **********************************************************************/

/******************************************************************************
 *  Results
 *****************************************************************************/
        1. IntegerSorter
                N                                disk used
              - 100............................. 47
              - 1,000........................... 517
              - 10,000.......................... 4,990
              - 100,000......................... 417,604
              - 1,000,000....................... 

       2. WorstFit
                N                                disk used
              - 100............................. 42
              - 1,000........................... 525
              - 10,000.......................... 4,973
              - 100,000......................... 207,813
              - 1,000,000....................... 

/******************************************************************************
 *  Order of growth Big(O)
 *****************************************************************************/
        1. WorstFit
              - Heap
                     - Big(O) of nlogn
                     - Heap performance is log N (excellent!):
                     - insert is O(log n) because the tree depth is log n.
                     - removeMin is O(log n)... the min is right there, O(1), but you have to swap and
                     - restore the heap, which is O( log n)
              - disk made 
                     - Big(O) of N * M 
                     - N = disk size
                     - M = heap size
        2. IntegerSorter
              - Array
                     - Big(O) of n
                     - insert is O(1) because the it inserts it at in instance.
                     - removeMin is.. the min is right there, O(1), but you have to swap and
                     - restore the heap, which is O(n)
              - disk made 
                     - Big(O) of N * M 
                     - N = disk size
                     - M = heap size
 
/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
        -not that im aware of


/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including
 *  classmates and friends) and attribute them by name.
 *****************************************************************************/
        - https://stackoverflow.com/questions/36726260/how-does-collections-sort-work

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
        - Making the disk was difficult
        - Bad partner


/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/

        - I enjoyed it 
        - It was fun. Just was a little confused on what InegerSorter.java 
        - was. I feel like a better explaination would've help on the word .dox