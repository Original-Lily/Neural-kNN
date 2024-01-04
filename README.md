# Neural-kNN 

**Specifications**

As part of my Introduction to Ai course I was tasked with creating an algorithm, that can accurately read data records from patients heads in order to measure the long term affects of alcahol abuse on the memory. This data would measure 61 different parameters from 200 separate patients in both the training data & the test data, with the desired output for each being a binary 1 or 0.

# Deployement

Ensure you run both kNN1.java and kNN2.java before comparing their outcomes using TestLabels.java

The length and / or accuracy of kNN2 can be tweeked by changing the parameters, early in the code; this will be subjective depending upon both your computer's specifications & luck reguarding mutations.
 
# Improvements to kNN accuracy in kNN2.java

**Feature Scaling**

One of the initial steps I took to enhance classification accuracy was feature scaling. This ensures that each aspect feature contributes proportionally to the distance computation, aiding with the influence outlying data can have, skewing statistical data. I achieved standardisation by subtracting the mean and dividing by the standard deviation.

**Genetic Algorithm**

A genetic algorithm was employd to dynamically select a subset of features that contribute to the most accurate predictions (i.e. their respective columns). This iteratively evolves through the feature sets in detail, applying mutations and selecting the subset with the fittest outcome. Notably, dynamic mutations were introduced where the mutation rate changes over time. The selected subset of features with the highest classification accuracy was stored as a blueprint for later predictions & comparison purposes.

**Manhattan Distances**

Instead of using the previously utilised Euclidean distances, I implemented the use of Manhattan distances, in order to capture different patterns in the data provided, and provide an increased accuracy.

**Helper methods**

Once these features had been implemented my program, it’s performance was much worse than to be expected. This was necessary in order to speed up the processing time for each iteration of my genetic algorithm and maximize time, accuracy efficiency. After significantly reducing the amount of duplicate processing throughout my algorithm and its subsequent O notation, I can say roughly 50,000 repeats can be accomplished in the time it would’ve taken 5,000, in a case without these changes.

**Conclusion drawn from improvements**

In conclusion, I have developed an improved kNN algorithm, providing a 35% – 50% improvement (depending upon the training dataset’s resemblance) and can confidently say this is much more suited to the needs specified. There are other improvements that can be made to this algorithm such as: a more varying K value, or (if permitted) exploring different distance metric calculations I researched such as minkowski or hamming distances.

I conducted a meta-analysis of the data provided and concluded certain columns were guaranteed to be blocked, and could have possibly implemented a variable in order to blacklist these, however this would be hard-coding. A screenshot + link to this standardisted data can be found here.
