export function removeUndefined<T extends object>(obj: T): T {
  const keys: string[] = Object.keys(obj);

  return keys.reduce<T>((current, key): T => {
    // @ts-ignore
    return obj[key] === undefined ? { ...current } : { ...current, [key]: obj[key] };
  }, {} as T);
}
