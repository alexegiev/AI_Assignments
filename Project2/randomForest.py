import numpy as np
from collections import Counter
from id3 import ID3
from sklearn.metrics import precision_score
from sklearn.tree import DecisionTreeClassifier

class RandomForest:
    def __init__(self, n_estimators=50, max_depth=None):
        self.n_estimators = n_estimators
        self.max_depth = max_depth

    def fit(self, X, y):
        self.clfs_ = []
        self.clf_weights_ = np.ones(self.n_estimators) / self.n_estimators  # Initializing equal weights for each tree

        for _ in range(self.n_estimators):
            clf = DecisionTreeClassifier(max_depth=self.max_depth)
            indices = np.random.choice(X.shape[0], size=X.shape[0], replace=True)
            clf.fit(X[indices], y[indices])

            self.clfs_.append(clf)

        return self

    def predict(self, X):
        clf_preds = np.array([clf.predict(X) for clf in self.clfs_])
        return np.sign(np.dot(self.clf_weights_, clf_preds))

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
