import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.svm import SVR
from sklearn.metrics import mean_squared_error, r2_score

# Load the data
data = pd.read_csv("C:\\Users\\oumta\\Desktop\\2022 Spring\\2023 spring\\Data\\RP\\score_data.csv")

# Split the data into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(data.drop('score', axis=1), data['score'], test_size=0.2)

# Train the SVM model
model = SVR(kernel='linear', C=1.0, epsilon=0.2)
model.fit(X_train, y_train)

# Evaluate the model
y_pred = model.predict(X_test)
print("Mean squared error: %.2f" % mean_squared_error(y_test, y_pred))
print('R2 score: %.2f' % r2_score(y_test, y_pred))

# Use the model to predict scores for new data
new_data = pd.DataFrame({'num_targets_hit': [10], 'time_elapsed': [120]})
predicted_score = model.predict(new_data)[0]
scaled_score = round(predicted_score * 100 / data['score'].max(), 2)
print("Predicted score: %.2f (scaled to 0-100 range)" % scaled_score)