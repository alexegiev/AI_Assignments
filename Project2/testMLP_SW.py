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
train_vectors = vectorizer.fit_transform(train_sentences).toarray()

# Transform the test data
test_vectors = vectorizer.transform(test_sentences).toarray()

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
    Dense(16, activation='relu', input_shape=(m,)),
    Dense(16, activation='relu'),
    Dense(1, activation='sigmoid')
])

# Compile the model
model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

# Train the model
history = model.fit(train_vectors, train_labels, epochs=10, validation_data=(test_vectors, test_labels))

# Make predictions on the test data
y_pred = model.predict(test_vectors)

# Convert the predictions to binary labels
y_pred_bin = [1 if y > 0.5 else 0 for y in y_pred]

# Calculate the accuracy, precision, recall, and F1 score
accuracy = accuracy_score(test_labels, y_pred_bin)
precision = precision_score(test_labels, y_pred_bin)
recall = recall_score(test_labels, y_pred_bin)
f1 = f1_score(test_labels, y_pred_bin)

print('MLP Accuracy: {:.2f}'.format(accuracy))
print('MLP Precision: {:.2f}'.format(precision))
print('MLP Recall: {:.2f}'.format(recall))
print('MLP F1 Score: {:.2f}'.format(f1))

import matplotlib.pyplot as plt

# Fit the model and record the training history
history = model.fit(train_vectors, train_labels, validation_data=(test_vectors, test_labels), epochs=10)

# Plot the training and validation accuracy
plt.figure()
plt.plot(history.history['accuracy'], 'r', label='Training accuracy')
plt.plot(history.history['val_accuracy'], 'b', label='Validation accuracy')
plt.xlabel('Epochs')
plt.ylabel('Accuracy')
plt.title('Training and Validation Accuracy Over Epochs')
plt.legend()
plt.grid()
plt.show()