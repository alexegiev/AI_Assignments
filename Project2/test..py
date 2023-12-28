from keras.datasets import imdb
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics import accuracy_score
from bayes import NaiveBayes  # assuming bayes.py is in the same directory

# Load the IMDB dataset
(train_data, train_labels), (test_data, test_labels) = imdb.load_data(num_words=10000)

# Convert the list of words to sentences
word_index = imdb.get_word_index()
reverse_word_index = dict([(value, key) for (key, value) in word_index.items()])
train_data = [" ".join([reverse_word_index.get(i - 3, "?") for i in sentence]) for sentence in train_data]
test_data = [" ".join([reverse_word_index.get(i - 3, "?") for i in sentence]) for sentence in test_data]

# Convert the sentences to a bag of words model
vectorizer = CountVectorizer(binary=True)
train_data = vectorizer.fit_transform(train_data).toarray()
test_data = vectorizer.transform(test_data).toarray()

# Initialize the NaiveBayes model
model = NaiveBayes()

# Fit the model with the training data
model.fit(train_data, train_labels)

# Predict the test data
predictions = model.predict(test_data)

# Evaluate the model
print("Accuracy:", accuracy_score(test_labels, predictions))