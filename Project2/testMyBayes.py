from keras.datasets import imdb
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics import accuracy_score
from Project2.myBayes import NaiveBayes  # assuming bayes.py is in the same directory
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

from Project2.myBayes import NaiveBayes
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score
from sklearn.model_selection import learning_curve
import matplotlib.pyplot as plt
import numpy as np

# Train and evaluate the Naive Bayes classifier
nb = NaiveBayes()
nb.fit(train_vectors, train_labels)
nb_predictions = nb.predict(test_vectors)
print("Naive Bayes Accuracy: ", accuracy_score(test_labels, nb_predictions))
print("Naive Bayes Precision: ", precision_score(test_labels, nb_predictions))
print("Naive Bayes Recall: ", recall_score(test_labels, nb_predictions))
print("Naive Bayes F1 Score: ", f1_score(test_labels, nb_predictions))

# Plot learning curves
train_sizes, train_scores, test_scores = learning_curve(NaiveBayes(), train_vectors, train_labels, cv=5)

train_scores_mean = np.mean(train_scores, axis=1)
test_scores_mean = np.mean(test_scores, axis=1)

plt.figure()
plt.title("Learning Curves (Naive Bayes)")
plt.xlabel("Training examples")
plt.ylabel("Score")
plt.grid()
plt.plot(train_sizes, train_scores_mean, 'o-', color="r", label="Training score")
plt.plot(train_sizes, test_scores_mean, 'o-', color="g", label="Cross-validation score")
plt.legend(loc="best")
plt.show()

# Predict labels for the test data
nb_predictions = nb.predict(test_vectors)