import numpy as np
from collections import Counter
from sklearn.metrics import precision_score
from id3 import ID3  # Import your ID3 class

class RandomForest:
    def __init__(self, n_estimators=50, max_depth=None):
        self.n_estimators = n_estimators
        self.max_depth = max_depth

    def fit(self, X, y):
        self.trees = []

        for _ in range(self.n_estimators):
            # Randomly select a subset of features
            features_subset = np.random.choice(X.shape[1], size=int(np.sqrt(X.shape[1])), replace=False)
            X_subset = X[:, features_subset]

            # Split the dataset into random subsets
            X_subsample, y_subsample = self._bootstrap_sample(X_subset, y)

            # Train an ID3 decision tree on the subset
            id3_tree = ID3(features=features_subset)
            tree_root = id3_tree.fit(X_subsample, y_subsample, max_depth=self.max_depth)

            self.trees.append(tree_root)

        return self

    def predict(self, X):
        predictions = np.array([self._predict_tree(tree, X) for tree in self.trees])
        return np.apply_along_axis(lambda x: Counter(x).most_common(1)[0][0], axis=0, arr=predictions)

    def _predict_tree(self, tree, X):
        predicted_classes = []
        for unlabeled in X:
            tmp = tree  # Start at the root
            while not tmp.is_leaf:
                if unlabeled.flatten()[tmp.checking_feature] == 1:
                    tmp = tmp.left_child
                else:
                    tmp = tmp.right_child

            predicted_classes.append(tmp.category)

        return np.array(predicted_classes)

    def _bootstrap_sample(self, X, y):
        indices = np.random.choice(X.shape[0], size=X.shape[0], replace=True)
        return X[indices], y[indices]

    def score(self, X, y):
        y_pred = self.predict(X)
        precision = precision_score(y, y_pred, average='binary', zero_division=1)
        return precision

    def get_params(self, deep=True):
        return {"n_estimators": self.n_estimators, "max_depth": self.max_depth}

    def set_params(self, **parameters):
        for parameter, value in parameters.items():
            setattr(self, parameter, value)
        return self
