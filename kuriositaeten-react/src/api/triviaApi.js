const BASE_URL = import.meta.env.DEV ? '/api' : import.meta.env.BASE_URL
export async function fetchCategories() {
  const res = await fetch(`${BASE_URL}/CategoriesServlet`)
  if (!res.ok) throw new Error(`Failed to fetch categories: ${res.status}`)
  return res.json()
}

/**
 * @param {Object} params
 * @param {string|number} params.category
 * @param {number} params.count
 * @param {string} params.difficulty
 * @param {string} params.type
 */
export async function fetchTrivia({ category, count, difficulty, type }) {
  const query = new URLSearchParams({
    category,
    count,
    difficulty,
    type
  }).toString()

  const res = await fetch(`${BASE_URL}/TriviaServlet?${query}`)
  if (!res.ok) throw new Error(`Failed to fetch trivia: ${res.status}`)
  return res.json()
}