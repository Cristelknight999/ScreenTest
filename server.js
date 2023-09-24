const express = require('express');
const axios = require('axios');
const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.static('public'));
app.use(express.json());

// Define an endpoint for searching GitHub repo files
app.post('/search', async (req, res) => {
  const { repoOwner, repoName, searchTerm } = req.body;

  try {
    const response = await axios.get(
      `https://api.github.com/repos/${repoOwner}/${repoName}/contents`,
      {
        params: {
          ref: 'main', // Replace with your desired branch
        },
      }
    );

    // Filter files by name containing the searchTerm
    const matchingFiles = response.data.filter((file) =>
      file.name.includes(searchTerm)
    );

    res.json(matchingFiles);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});