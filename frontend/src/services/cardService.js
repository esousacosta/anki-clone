import api from "./api";

const cardService = {
    reviewCard: async (cardId, rating, signal) => {
        const response = await api.post(
            `/cards/${cardId}/review`,
            { 'quality': rating },
            signal ? { signal } : undefined
        );
        return response.data;
    }
};

export default cardService;