import numpy as np

class NaiveBayes:
    def __init__(self):
        self.classes = None
        self.class_priors = None
        self.feature_probs = None

    def fit(self, X, y):
        self.classes = np.unique(y)
        self.class_priors = np.zeros(len(self.classes))
        self.feature_probs = []

        for i, c in enumerate(self.classes):
            X_c = X[y == c]
            self.class_priors[i] = len(X_c) / len(X)

            feature_probs_c = []
            for j in range(X.shape[1]):
                feature_values = np.unique(X[:, j])
                feature_probs_c.append(
                    {value: ((X_c[:, j] == value).sum() + 1) / (len(X_c) + len(feature_values))
                     for value in feature_values}
                )
            self.feature_probs.append(feature_probs_c)

    def predict(self, X):
        predictions = []
        for x in X:
            class_scores = []
            for i, c in enumerate(self.classes):
                class_score = np.log(self.class_priors[i])
                for j, feature_value in enumerate(x):
                    class_score += np.log(self.feature_probs[i][j].get(feature_value, 1e-9))
                class_scores.append(class_score)
            predictions.append(self.classes[np.argmax(class_scores)])
        return predictions
