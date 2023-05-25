import pandas as pd
import numpy as np

# read the data file into a pandas dataframe
data = pd.read_csv("C:\\Users\\oumta\\Desktop\\2022 Spring\\2023 spring\\Data\\processed.cleveland.data", header=None)

# replace '?' values with NaN
data.replace('?', np.nan, inplace=True)

# impute missing values with the mean value of the column
data.fillna(data.mean(), inplace=True)

# convert the target variable to binary classification (0 = healthy, 1 = sick)
data[13] = np.where(data[13] > 0, 1, 0)

# split the data into input features (X) and target variable (y)
X = data.iloc[:, :-1]
y = data.iloc[:, -1]

# perform SVM classification with 10-fold cross-validation
from sklearn.svm import SVC
from sklearn.model_selection import cross_val_score

svm_model = SVC(kernel='linear')

# test different values of max_iter parameter
for max_iter in [10, 100, 1000, 10000]:
    svm_model.set_params(max_iter=max_iter)
    scores = cross_val_score(svm_model, X, y, cv=10)
    print("max_iter: {}, accuracy: {:.2f}".format(max_iter, scores.mean()))