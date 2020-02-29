export interface IAssures {
  id?: number;
  noassure?: string;
  nom?: string;
  prenom?: string;
}

export class Assures implements IAssures {
  constructor(public id?: number, public noassure?: string, public nom?: string, public prenom?: string) {}
}
