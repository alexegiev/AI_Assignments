import numpy as np
class AdaBoost:
    def __init__(self, n_estimators=5, learning_rate=1.0):
        self.n_estimators = n_estimators
        self.learning_rate = learning_rate
        self.models = []
        self.alphas = []

    def fit(self, X, y):
        n_samples, n_features = X.shape
        weights = np.ones(n_samples) / n_samples

        for _ in range(self.n_estimators):
            stump = Stump()
            min_error = float('inf')

            for feature_i in range(n_features):
                feature_values = np.expand_dims(X[:, feature_i], axis=1)
                unique_values = np.unique(feature_values)

                for threshold in unique_values:
                    p = 1
                    prediction = np.ones(np.shape(y))

                    prediction[X[:, feature_i] < threshold] = -1

                    error = sum(weights[y != prediction])

                    if error > 0.5:
                        error = 1 - error
                        p = -1

                    if error < min_error:
                        stump.polarity = p
                        stump.threshold = threshold
                        stump.feature_index = feature_i
                        min_error = error

            stump.alpha = self.learning_rate * (np.log((1.0 - min_error) / (min_error + 1e-10)))
            weights *= np.exp(-stump.alpha * y * stump.predict(X))
            weights /= np.sum(weights)

            self.models.append(stump)

    def predict(self, X):
        n_samples = X.shape[0]
        y_pred = np.zeros((n_samples, 1))

        for stump in self.models:
            predictions = stump.predict(X)
            y_pred += stump.alpha * predictions

        y_pred = np.sign(y_pred).flatten()

        return y_pred
class Stump:
    def __init__(self):
        self.polarity = 1
        self.feature_index = None
        self.threshold = None
        self.alpha = None

    def predict(self, X):
        n_samples = X.shape[0]
        X_column = X[:, self.feature_index]
        predictions = np.ones(n_samples)
        if self.polarity == 1:
            predictions[X_column < self.threshold] = -1
        else:
            predictions[X_column > self.threshold] = -1

        return predictions
