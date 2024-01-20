from keras.datasets import imdb
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.ensemble import RandomForestClassifier  # Import RandomForestClassifier
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score
from sklearn.model_selection import learning_curve
import matplotlib.pyplot as plt
from collections import Counter
import numpy as np

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

# Convert sentences to vectors
vectorizer = CountVectorizer(vocabulary=vocab)
train_vectors = vectorizer.transform(train_sentences).toarray()
test_vectors = vectorizer.transform(test_sentences).toarray()

# Train the RandomForestClassifier
random_forest = RandomForestClassifier(n_estimators=50, max_depth=None)
random_forest.fit(train_vectors, train_labels)

# Make predictions on the test set
random_forest_predictions = random_forest.predict(test_vectors)

# Calculate the number of correct predictions
correct_predictions = np.sum(random_forest_predictions == test_labels)

# Calculate the accuracy
accuracy = correct_predictions / len(test_labels)

print(f"Accuracy: {accuracy * 100:.2f}%")

# Evaluate performance metrics
print("RandomForest Accuracy: ", accuracy_score(test_labels, random_forest_predictions))
print("RandomForest Precision: ", precision_score(test_labels, random_forest_predictions, average='macro', zero_division=1))
print("RandomForest Recall: ", recall_score(test_labels, random_forest_predictions, average='macro', zero_division=1))
print("RandomForest F1 Score: ", f1_score(test_labels, random_forest_predictions, average='macro', zero_division=1))

# Plot learning curves
train_sizes, train_scores, test_scores = learning_curve(RandomForestClassifier(n_estimators=50, max_depth=None), train_vectors, train_labels, cv=5)

train_scores_mean = np.mean(train_scores, axis=1)
test_scores_mean = np.mean(test_scores, axis=1)

plt.plot(train_sizes, train_scores_mean, label='Training score')
plt.plot(train_sizes, test_scores_mean, label='Cross-validation score')
plt.title('Learning Curves for RandomForestClassifier')
plt.xlabel('Training examples')
plt.ylabel('Score')
plt.legend(loc='best')
plt.show()

# Predict labels for the test data
random_forest_predictions = random_forest.predict(test_vectors)

# Print out some of the reviews with their predicted labels
for review, label in zip(test_sentences[:10], random_forest_predictions[:10]):
    print(f"Review: {review}\nPredicted label: {'Good' if label == 1 else 'Bad'}\n")