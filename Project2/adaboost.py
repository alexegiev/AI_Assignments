from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import precision_score
import numpy as np 

class AdaBoost:
    def __init__(self, n_estimators=50, learning_rate=0.01):
        self.n_estimators = n_estimators
        self.learning_rate = learning_rate

    def fit(self, X, y):
        self.clfs_ = []
        self.clf_weights_ = np.zeros(self.n_estimators)
        self.errors_ = np.zeros(self.n_estimators)

        sample_weights = np.full(X.shape[0], (1 / X.shape[0]))

        for iboost in range(self.n_estimators):
            clf = DecisionTreeClassifier(max_depth=1)
            clf.fit(X, y, sample_weight=sample_weights)
            pred = clf.predict(X)

            incorrect = (pred != y)
            error = np.mean(np.average(incorrect, weights=sample_weights, axis=0))
            alpha = self.learning_rate * np.log((1 - error) / error)

            sample_weights *= np.exp(alpha * incorrect * ((sample_weights > 0) | (alpha < 0)))

            self.clfs_.append(clf)
            self.clf_weights_[iboost] = alpha
            self.errors_[iboost] = error

        return self

    def predict(self, X):
        clf_preds = np.array([clf.predict(X) for clf in self.clfs_])
        return np.sign(np.dot(self.clf_weights_, clf_preds))

    def score(self, X, y):
        y_pred = self.predict(X)
        precision = precision_score(y, y_pred, average='binary', zero_division=1)
        return precision
    
    def get_params(self, deep=True):
        return {"n_estimators": self.n_estimators, "learning_rate": self.learning_rate}

    def set_params(self, **parameters):
        for parameter, value in parameters.items():
            setattr(self, parameter, value)
        return self
    
