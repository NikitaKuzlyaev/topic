const config = {
    API_BASE_URL: 'http://localhost:8080'

};


export const getApiUrl = (endpoint) => {
    return `${config.API_BASE_URL}${endpoint}`;
};

export default config;