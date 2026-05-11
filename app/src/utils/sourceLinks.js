const REPO_BLOB_BASE_URL = 'https://github.com/rodrigofujioka/aluno_online_pos_2026/blob/main/';
const REPO_TREE_BASE_URL = 'https://github.com/rodrigofujioka/aluno_online_pos_2026/tree/main/';

const normalizeBaseUrl = (baseUrl) => baseUrl.replace(/\/?$/, '/');
const normalizePath = (path) => path.replace(/\\/g, '/').replace(/^\//, '');

export function buildRepoFileUrl(path) {
  return `${normalizeBaseUrl(REPO_BLOB_BASE_URL)}${normalizePath(path)}`;
}

export function buildRepoTreeUrl(path) {
  const normalizedPath = normalizePath(path);
  const segments = normalizedPath.split('/');
  const folderPath = segments.length > 1 ? segments.slice(0, -1).join('/') : normalizedPath;
  return `${normalizeBaseUrl(REPO_TREE_BASE_URL)}${folderPath}`;
}

export function hasRepoLinks() {
  return true;
}
