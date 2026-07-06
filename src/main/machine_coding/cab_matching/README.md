# Problem Statement
## Description 
A cab company has the list of cab rides after it gets completed. After the journey both cab cabDriver and trip taker provide rating to each other. Rating can be from 1 to 5 where 5 is the best rating and 1 being the worst. 

Given a Ride taker name, find the eligible cab drivers for him based on the historical data that is being given to you. The criteria's are given below to choose the eligible cab drivers - 

1. Avg rating of Cab cabDriver should be Greater than average rating of trip taker (Choose with the highest rating) 
2. If no cab cabDriver is found to consider, the best cabDriver of all of them must be assigned.
3. If any cab cabDriver has provided 1 rating to a trip taker or vice versa then they are not allowed to do a trip together. 

### Input 
Ride data is given (Ride Taker Name Rating given to Ride Taker . Driver Name. Rating given to cabDriver)

Ride 1 - (Ram, 3, Bheem, 5)  
Ride 2 - (Laxman, 5, Nakul, 2)  
Ride 3 - (Ram, 4, Sahadev, 2)  
Ride 4 - (Bharat, 3, Bheem, 5)  
Ride 5 - (Ram, 3, Bheem, 1)  
Ride 6 - (Laxman, 5, Sahadev, 3)  
Ride 7 - (Bharat, 5, Nakul, 5)  

Q. Find eligible cabDriver for  
    1. Ram  
    2. Laxman  

A.  1. Nakul  
    2. Bheem

Explanation - 

Rider -
Ram Avg Rating - 3.33
Laxman Avg Rating - 5

Driver - 
Bheem Avg Rating - 3.66
Nakul Avg Rating - 3.5
Sahadev Avg Rating - 2.5