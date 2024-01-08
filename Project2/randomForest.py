import numpy as np
from collections import Counter
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score

# Assume you have a function to preprocess your texts and extract features
def preprocess_texts(texts, vocabulary):
    # Implement your text preprocessing here
    pass

# ID3 decision tree implementation (you should have this implemented)
def id3_decision_tree(X, y, max_depth=None):
    # Implement your ID3 decision tree here
    pass

# Random Forest implementation
class RandomForest:
    def __init__(self, n_trees, max_depth=None):
        self.n_trees = n_trees
        self.max_depth = max_depth
        self.trees = []

    def fit(self, X, y):
        for _ in range(self.n_trees):
            # Randomly select a subset of features
            features_subset = np.random.choice(X.shape[1], size=int(np.sqrt(X.shape[1])), replace=False)
            X_subset = X[:, features_subset]
            
            # Split the dataset into random subsets
            X_subsample, y_subsample = self._bootstrap_sample(X_subset, y)
            
            # Train an ID3 decision tree on the subset
            tree = id3_decision_tree(X_subsample, y_subsample, max_depth=self.max_depth)
            self.trees.append(tree)

    def predict(self, X):
        # Make predictions using each tree and return the majority vote
        predictions = np.array([tree.predict(X) for tree in self.trees])
        return np.apply_along_axis(lambda x: Counter(x).most_common(1)[0][0], axis=0, arr=predictions)

    def _bootstrap_sample(self, X, y):
        # Randomly sample with replacement to create a subset
        indices = np.random.choice(X.shape[0], size=X.shape[0], replace=True)
        return X[indices], y[indices]
