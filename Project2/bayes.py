import numpy as np

class NaiveBayes:
    def __init__(self):
        self.classes = None
        self.class_priors = None
        self.feature_probs = None

    def fit(self, X, y):
        y = np.array(y)  # Convert y to a NumPy array
        self.classes = np.unique(y)
        self.class_priors = np.zeros(len(self.classes))
        self.feature_probs = []

        for i, c in enumerate(self.classes):
            X_c = X[y == c]
            self.class_priors[i] = X_c.shape[0] / X.shape[0]

            feature_probs_c = []
            for j in range(X.shape[1]):
                feature_values = np.unique(X[:, j].toarray())
                feature_probs = {value: (np.sum(X_c[:, j].toarray() == value) + 1) / (X_c.shape[0] + len(feature_values)) for value in feature_values}
                feature_probs_c.append(feature_probs)
            self.feature_probs.append(feature_probs_c)

    def predict(self, X):
        predictions = []
        for x in X:
            x = x.toarray()[0]  # Convert x to a dense array
            class_scores = []
            for i, c in enumerate(self.classes):
                class_score = np.log(self.class_priors[i])
                for j, feature_value in enumerate(x):
                    class_score += np.log(self.feature_probs[i][j].get(feature_value, 1e-9))
                class_scores.append(class_score)
            predictions.append(self.classes[np.argmax(class_scores)])
        return predictions
    
    def score(self, X, y):
        predictions = self.predict(X)
        return np.mean(predictions == y)
    
    def get_params(self, deep=True):
        return {}  # NaiveBayes has no parameters

    def set_params(self, **parameters):
        for parameter, value in parameters.items():
            setattr(self, parameter, value)
        return self