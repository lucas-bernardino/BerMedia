import { IUser } from "./interfaces";

export function setTokenLocalStorage(token: string) {
  localStorage.setItem("token", token);
}

export function getTokenLocalStorage(): string | null {
  return localStorage.getItem("token");
}

export async function getAuthenticatedUser(): Promise<IUser | null> {
  try {
    // const user: IUser | null = null;
    const token = getTokenLocalStorage();
    if (!token) {
      return null;
    }
    const response = await fetch("http://localhost:8080/auth/me", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if (response.status == 404) {
      return null;
    }
    if (response.status == 200) {
      return await response.json();
    }
    throw new Error("Unexpected status code: ${response.status}");
  } catch (error) {
    throw new Error("Something went wrong in getting the authenticated user");
  }
}
