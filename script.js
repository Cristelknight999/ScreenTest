document.addEventListener('DOMContentLoaded', () => {
  const searchInput = document.getElementById('searchInput');
  const searchButton = document.getElementById('searchButton');
  const searchResults = document.getElementById('searchResults');

  searchButton.addEventListener('click', async () => {
    const searchTerm = searchInput.value.trim();
    if (!searchTerm) return;

    const repoOwner = 'Cristelknight999';
    const repoName = 'WWOO';

    try {
      const response = await fetch('/search', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ repoOwner, repoName, searchTerm }),
      });

      if (response.ok) {
        const matchingFiles = await response.json();
        displayResults(matchingFiles);
      } else {
        console.error('Error searching for files' + JSON.stringify({ repoOwner, repoName, searchTerm }));
      }
    } catch (error) {
      console.error(error);
    }
  });

  function displayResults(files) {
    searchResults.innerHTML = '';

    if (files.length === 0) {
      searchResults.innerHTML = '<p>No matching files found.</p>';
      return;
    }

    files.forEach((file) => {
      const listItem = document.createElement('li');
      listItem.textContent = file.name;
      searchResults.appendChild(listItem);
    });
  }
});
