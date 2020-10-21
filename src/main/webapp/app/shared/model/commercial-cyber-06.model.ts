export interface ICommercialCyber06 {
  id?: number;
  firstname?: string;
  name?: string;
}

export class CommercialCyber06 implements ICommercialCyber06 {
  constructor(public id?: number, public firstname?: string, public name?: string) {}
}
