import api from "./api";

/**
 * Deck service - network calls related to decks.
 * The service does not manage React state. It returns data or throws errors.
 */
const deckService = {
  /**
   * Fetch all decks from the backend.
   * @param {AbortSignal} [signal] optional AbortSignal to cancel the request
   * @returns {Promise<Array>} array of decks
   */
  async fetchDecks(signal) {
    // Pass the signal through to the http client. If the client doesn't support
    // AbortController, adapt this method (e.g. axios cancel tokens) accordingly.
    const response = await api.get("/decks", signal ? { signal } : undefined);
    // Return the parsed data for the caller to handle state updates.
    return response.data;
  },

  async fetchDeckById(deckId, signal) {
    const response = await api.get(
      `/decks/${deckId}`,
      signal ? { signal } : undefined
    );
    return response.data;
  },

  async fetchDeckCardsToReviewById(deckId, signal) {
    const response = await api.get(
      `/decks/${deckId}/review`,
      signal ? { signal } : undefined
    );
    return response.data;
  },
};

export default deckService;
