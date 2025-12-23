import api from "./api";

const cardService = {
  reviewCard: async (cardId, rating, signal) => {
    try {
      const response = await api.post(
        `/cards/${cardId}/review`,
        { quality: rating },
        signal ? { signal } : undefined
      );
      return response.data;
    } catch (error) {
      console.error("Error reviewing card:", error);
      throw error;
    }
  },
};

export default cardService;