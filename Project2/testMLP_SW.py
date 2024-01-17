from keras.datasets import imdb
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics import accuracy_score
from collections import Counter

# Load the IMDB dataset
(train_data, train_labels), (test_data, test_labels) = imdb.load_data(num_words=1000)

# Convert the list of words to sentences
word_index = imdb.get_word_index()
reverse_word_index = dict([(value, key) for (key, value) in word_index.items()])
train_sentences = [" ".join([reverse_word_index.get(i - 3, "?") for i in sentence]) for sentence in train_data]
test_sentences = [" ".join([reverse_word_index.get(i - 3, "?") for i in sentence]) for sentence in test_data]

# Define the hyperparameters
n = 10
m = 1000
k = 10

# Convert the reviews into a list of words
train_words = [word for sentence in train_sentences for word in sentence.split()]

# Count the frequency of each word
word_freq = Counter(train_words)

# Exclude the n most frequent and the k rarest words
vocab = {word for word, freq in word_freq.most_common()[n:-k]}

# Only keep the m most frequent words
vocab = list(vocab)[:m]

# Create a dictionary that maps each word to its index in the vocabulary
word_index = {word: i for i, word in enumerate(vocab)}

# Convert the reviews into binary feature vectors
train_vectors = [[1 if word in sentence.split() else 0 for word in vocab] for sentence in train_sentences]
test_vectors = [[1 if word in sentence.split() else 0 for word in vocab] for sentence in test_sentences]

# Create the CountVectorizer
vectorizer = CountVectorizer(binary=True, max_features=m)

# Fit the CountVectorizer to the training data and transform the training data
train_vectors = vectorizer.fit_transform(train_sentences)

# Transform the test data
test_vectors = vectorizer.transform(test_sentences)

from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score
from sklearn.model_selection import learning_curve
import matplotlib.pyplot as plt
import numpy as np

from keras.models import Sequential
from keras.layers import Dense, Flatten
from keras.preprocessing.sequence import TimeseriesGenerator
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score

# Define the window size for the sliding window
window_size = 5

# Get the number of features in the data
m = train_vectors.shape[1]
    
# Define the MLP model
model = Sequential([
    Flatten(input_shape=(window_size, m)),
    Dense(16, activation='relu'),
    Dense(16, activation='relu'),
    Dense(1, activation='sigmoid')
])

# Compile the model
model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

# Create the sliding window data
train_gen = TimeseriesGenerator(train_vectors.toarray(), train_labels, length=window_size, batch_size=32)
test_gen = TimeseriesGenerator(test_vectors.toarray(), test_labels, length=window_size, batch_size=32)

# Train the model
history = model.fit(train_gen, epochs=10, validation_data=test_gen)

# Extract the data and labels from the generators
test_data, test_labels = zip(*[(data, labels) for data, labels in test_gen])

# Flatten the test data and labels
test_data_flat = np.concatenate(test_data)
test_labels_flat = np.concatenate(test_labels)

# Make predictions on the test data
y_pred = model.predict(test_data_flat)

# Convert the predictions to binary labels
y_pred_bin = [1 if y > 0.5 else 0 for y in y_pred]

# Calculate the accuracy, precision, recall, and F1 score
accuracy = accuracy_score(test_labels_flat, y_pred_bin)
precision = precision_score(test_labels_flat, y_pred_bin)
recall = recall_score(test_labels_flat, y_pred_bin)
f1 = f1_score(test_labels_flat, y_pred_bin)

print('MLP Accuracy: {:.2f}'.format(accuracy))
print('MLP Precision: {:.2f}'.format(precision))
print('MLP Recall: {:.2f}'.format(recall))
print('MLP F1 Score: {:.2f}'.format(f1))

from sklearn.model_selection import learning_curve
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score
import matplotlib.pyplot as plt
import pandas as pd

# Fit the model and record the training history
history = model.fit(train_vectors, train_labels, validation_data=(test_vectors, test_labels), epochs=100)

# Generate learning curves
train_sizes, train_scores, test_scores = learning_curve(model, train_vectors, train_labels, cv=5)

# Calculate the mean training and test scores
train_scores_mean = np.mean(train_scores, axis=1)
test_scores_mean = np.mean(test_scores, axis=1)

# Plot the learning curves
plt.figure()
plt.plot(train_sizes, train_scores_mean, 'o-', color="r", label="Training score")
plt.plot(train_sizes, test_scores_mean, 'o-', color="g", label="Cross-validation score")
plt.xlabel("Training examples")
plt.ylabel("Score")
plt.title("Learning Curves")
plt.legend(loc="best")
plt.grid()
plt.show()

# Calculate metrics
accuracy = accuracy_score(test_labels, y_pred)
precision = precision_score(test_labels, y_pred)
recall = recall_score(test_labels, y_pred)
f1 = f1_score(test_labels, y_pred)

# Create a DataFrame of the metrics
metrics = pd.DataFrame({'Accuracy': accuracy, 'Precision': precision, 'Recall': recall, 'F1': f1}, index=[0])
print(metrics)

# Plot the training and validation loss over epochs
plt.figure()
plt.plot(history.history['loss'], 'r', label='Training loss')
plt.plot(history.history['val_loss'], 'b', label='Validation loss')
plt.xlabel('Epochs')
plt.ylabel('Loss')
plt.title('Training and Validation Loss Over Epochs')
plt.legend()
plt.grid()
plt.show()