import numpy as np
class adaboost:
    def __init__(self, n_estimators=50, learning_rate=1.0):
        self.n_estimators = n_estimators
        self.learning_rate = learning_rate
        self.models = []
        self.alphas = []
        self.classes = None

    def fit(self, X, y):
        self.classes = np.unique(y)
        y = np.where(y == self.classes[0], 1, -1)
        weights = np.ones(len(X)) / len(X)
        for _ in range(self.n_estimators):
            stump = DecisionTreeClassifier(max_depth=1)
            stump.fit(X, y, sample_weight=weights)
            predictions = stump.predict(X)
            error = weights[(predictions != y)].sum()
            alpha = self.learning_rate * (np.log(1 - error) - np.log(error)) + np.log(len(self.classes) - 1)
            weights *= np.exp(alpha * y * predictions)
            weights /= weights.sum()
            self.models.append(stump)
            self.alphas.append(alpha)

    def predict(self, X):
        predictions = []
        for x in X:
            class_scores = np.zeros(len(self.classes))
            for i, model in enumerate(self.models):
                class_scores[self.classes == model.predict(x)[0]] += self.alphas[i]
            predictions.append(self.classes[np.argmax(class_scores)])
        return predictions      